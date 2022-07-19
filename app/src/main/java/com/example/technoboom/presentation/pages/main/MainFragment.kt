package com.example.technoboom.presentation.pages.main

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Base64OutputStream
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.technoboom.BuildConfig
import com.example.technoboom.MainActivity
import com.example.technoboom.R
import com.example.technoboom.adapters.ImageAdapter
import com.example.technoboom.dataBase.entity.ImageEntity
import com.example.technoboom.databinding.FragmentMainBinding
import com.example.technoboom.models.AuthData
import com.example.technoboom.models.Data
import com.example.technoboom.models.files.SendData
import com.example.technoboom.models.resPonseUpload.ResUpload
import com.example.technoboom.presentation.pages.basePage.BaseFragment
import com.example.technoboom.utils.*
import com.example.technoboom.utils.AppConstant.CAMERA_POS
import com.example.technoboom.utils.AppConstant.DATE_FORMAT
import com.example.technoboom.utils.AppConstant.GALLERY_POS
import com.example.technoboom.utils.AppConstant.IMAGE_FORMAT
import com.example.technoboom.vm.AuthVm
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : BaseFragment(R.layout.fragment_main) {
    private val binding:FragmentMainBinding by viewBinding()
    private lateinit var listData:ArrayList<Data>
    private val appCompositionRoot get() = (activity as MainActivity).appCompositionRoot
    private val authVm:AuthVm by viewModels()
    private lateinit var photoURI: Uri
    private lateinit var imagePath:String
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var gson: Gson
    private val sharedPreference get() = authVm.sharedPreference
    private var isUpdate = false
    private var imageEntity:ImageEntity? = null
    private var updatePosition:Int = -1
    private var categoryText:String?=null
    private lateinit var listDataRv:ArrayList<com.example.technoboom.models.files.Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listData = ArrayList()
        gson = Gson()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
           mainViewCreated()
            dataBaseDataEmptyOrNull()
            pinfl.doAfterTextChanged {
                if (it.toString().isNotNullOrEmpty()){
                    buttonSave.enabledTrue()
                }
            }






            sendButton.setOnClickListener {
                authVm.getAllList().forEach {
                    val convertImageFileToBase64 = convertImageFileToBase64(File(it.imagePath))
                    listData.add(Data(convertImageFileToBase64,format = it.imagePath.substring(it.imagePath.length-3),it.categoryImage))
                }
                authVm.auth(AuthData(listData,sharedPreference.orderNumber.toString(),sharedPreference.pinfl.toString()))
              launch {
                  authVm.authResponse.fetchResult(appCompositionRoot.uiControllerApp){ responseBody ->
                      val resUpload = gson.fromJson(responseBody?.string().toString(), ResUpload::class.java)
                      if (resUpload!=null && resUpload.Success){
                          launch {
                              authVm.deleteAllDataTableImage()
                              mainViewCreated()
                              clearMyFiles()
                          }
                      }
                  }
              }
            }

            buttonSave.setOnClickListener {
                val pinfl = pinfl.text.toString().trim()
                val orderNumber = orderNumber.text.toString().trim()
                if (pinfl.isNotNullOrEmpty() && orderNumber.isNotNullOrEmpty()){
                    val savePinflAndOrderNumber = authVm.savePinflAndOrderNumber(pinfl, orderNumber)
                    if (savePinflAndOrderNumber){
                        linear.gone()
                        val loadAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_view)
                        coordinator.visible()
                        coordinator.animation = loadAnimation
                        getFiles()
                    }
                }else{
                    appCompositionRoot.errorDialog(
                        appCompositionRoot.mActivity.getString(R.string.error_input),
                        appCompositionRoot.mActivity.getString(R.string.empty_pinfl_or_order_number),
                        -2){
                    // TODO: Clicked button dialog
                    }
                }
            }

            uploadBtn.setOnClickListener {
                isUpdate = false
                uploadDataPermission()
            }




            clearCard.setOnClickListener {
                appCompositionRoot.deleteDialog(2){
                    if (it) {
                        launch {
                            authVm.sharedPreference.clear()
                            authVm.deleteAllDataTableImage()
                            clearMyFiles()
                            mainViewCreated()
                        }
                    }
                }
            }


        }
    }

    private fun mainViewCreated() {
        binding.apply {
                if (authVm.sharedPreference.orderNumber.isNotNullOrEmpty()){
                    getFiles()
                    coordinator.visible()
                    linear.gone()
                }else{
                    coordinator.gone()
                    linear.visible()
                }
        }
    }


    fun getFiles(){
        listDataRv = ArrayList()
            binding.apply {
                launch {
                    val pinfl = authVm.sharedPreference.pinfl.toString()
                    val orderNumber = authVm.sharedPreference.orderNumber.toString()
                        authVm.getFiles(SendData(pinfl,orderNumber))
                        authVm.files.fetchResult(appCompositionRoot.uiControllerApp){ result->
                            listDataRv = result?.Data as ArrayList<com.example.technoboom.models.files.Data>
                            imageAdapter = ImageAdapter(listDataRv,object:ImageAdapter.OnItemClickListener{
                                override fun onItemClick(data: com.example.technoboom.models.files.Data, position: Int) {

                                }
                            })
                            imageAdapter.listData = listDataRv
                            rv.adapter = imageAdapter
                        }
                }

                authVm.getAllImageEntity().observe(appCompositionRoot.mLifecycleOwner){
                    if (it.isNotEmpty()){
                        it.onEach {
                            listDataRv.add(com.example.technoboom.models.files.Data(
                                convertImageFileToBase64(File(it.imagePath)),
                                it.imagePath.substring(it.imagePath.length-3),
                                it.categoryImage, isSend = false))
                            imageAdapter.notifyDataSetChanged()
                        }
                    }
                }






            }
    }


    fun dataBaseDataEmptyOrNull(){
        if (!authVm.getAllList().isNullOrEmpty()){
            binding.sendButton.visible()
        }else{
            binding.sendButton.gone()
        }
    }

    fun uploadDataPermission(){
        binding.apply {
            appCompositionRoot.permissionDialog { position,cat ->
                if (cat.isNotNullOrEmpty()){
                    when(position){
                        CAMERA_POS->{
                            var imageFile = createImageFile()
                            photoURI = FileProvider.getUriForFile(appCompositionRoot.mContext, BuildConfig.APPLICATION_ID,imageFile)
                            getTakeImageContent.launch(photoURI)
                        }
                        GALLERY_POS->{
                            picImageForNewGallery()
                        }
                    }
                    categoryText = cat     
                }else{
                    Toast.makeText(requireContext(), "Раздел не выбран", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val date = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date())
        val externalFilesDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_$date",IMAGE_FORMAT,externalFilesDir).apply { absolutePath }
    }

    //Camera Upload
    private val getTakeImageContent = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            var openInputStream = activity?.contentResolver?.openInputStream(photoURI)
            var format = SimpleDateFormat(DATE_FORMAT,Locale.getDefault()).format(Date())
            var file = File(activity?.filesDir, "$format.jpg")
            var fileoutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileoutputStream)
            openInputStream?.close()
            fileoutputStream.close()
            var filAbsolutePath = file.absolutePath
            imagePath = filAbsolutePath
            launch {
                if (isUpdate && updatePosition!=-1){
                    imageEntity?.imagePath = file.absolutePath
                    authVm.updateImageEntity(imageEntity!!)
                    imageAdapter.notifyItemChanged(updatePosition)
                }else{
                    authVm.saveImageEntity(ImageEntity(imagePath = filAbsolutePath, categoryImage = categoryText.toString()))
                    dataBaseDataEmptyOrNull()
                }
            }
        }
    }



    //Gallery
    private fun picImageForNewGallery() {
        getImageContent.launch("image/*")
    }

    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?:return@registerForActivityResult
        var openInputStream =(activity)?.contentResolver?.openInputStream(uri)
        var filesDir = (activity)?.filesDir
        var format = SimpleDateFormat(DATE_FORMAT,Locale.getDefault()).format(Date())
        var file = File(filesDir,"$format$IMAGE_FORMAT")
        val fileOutputStream = FileOutputStream(file)
        openInputStream!!.copyTo(fileOutputStream)
        openInputStream.close()
        fileOutputStream.close()
        var filAbsolutePath = file.absolutePath
        imagePath = filAbsolutePath
        launch {
            if (isUpdate){
                imageEntity?.imagePath = file.absolutePath
                authVm.updateImageEntity(imageEntity!!)
                imageAdapter.notifyItemChanged(updatePosition)
            }else{
                authVm.saveImageEntity(ImageEntity(imagePath = filAbsolutePath,categoryImage = categoryText.toString()))
                dataBaseDataEmptyOrNull()
            }
        }
    }


    fun convertImageFileToBase64(imageFile: File): String {
        return ByteArrayOutputStream().use { outputStream ->
            Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                imageFile.inputStream().use { inputStream ->
                    inputStream.copyTo(base64FilterStream)
                }
            }
            return@use outputStream.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearMyFiles()
    }

    fun clearMyFiles() {
        val files = activity?.filesDir?.listFiles()
        if (files != null) for (file in files) {
            file.delete()
        }
    }
}