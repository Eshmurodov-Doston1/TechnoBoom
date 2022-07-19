package com.example.technoboom.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.technoboom.R
import com.example.technoboom.databinding.DialogCameraBinding
import com.example.technoboom.databinding.DialogEditeBinding
import com.example.technoboom.databinding.DialogErrorBinding
import com.example.technoboom.models.resPonseUpload.ResUpload
import com.example.technoboom.utils.AppConstant.CAMERA_POS
import com.example.technoboom.utils.AppConstant.EMPTY_STRING
import com.example.technoboom.utils.AppConstant.FILE_POS
import com.example.technoboom.utils.AppConstant.GALLERY_POS

class DialogHelper(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val activity: AppCompatActivity
) {


    fun errorDialog(
        titleError: String,
        infoError: String = EMPTY_STRING,
        code: Int,
        onClick: () -> Unit
    ) {
        val dialog = AlertDialog.Builder(context).create()
        val dialogBind = DialogErrorBinding.inflate(layoutInflater)
        dialog.setView(dialogBind.root)
        dialogBind.apply {
            when (code) {
                -1 -> {
                    tvTitleError.text = "Connection"
                    tvInfoError.text = context.getString(R.string.no_internet)
                    imgContainer.setImageResource(R.drawable.ic_no_connection)
                    btnConfirm.setOnClickListener {
                        onClick.invoke()
                        dialog.dismiss()
                    }
                }
                -2-> {
                    tvTitleError.text = titleError
                    tvInfoError.text = infoError
                    imgContainer.setImageResource(R.drawable.ic_warning)
                    btnConfirm.setOnClickListener {
                        onClick.invoke()
                        dialog.dismiss()
                    }
                }
                401 -> {
                    tvTitleError.text = titleError
                    tvInfoError.text = context.getString(R.string.error_server)
                    imgContainer.setImageResource(R.drawable.ic_warning)
                    btnConfirm.setOnClickListener {
                        onClick.invoke()
                        dialog.dismiss()
                    }
                }
                400 -> {
                    //bad request body wrong or wrong information was sent or something else
                    tvTitleError.text = titleError
                    imgContainer.setImageResource(R.drawable.ic_warning)
                    btnConfirm.setOnClickListener {
                        onClick.invoke()
                        dialog.dismiss()

                    }
                }
                in 421..499 -> {
                    tvTitleError.text = titleError
                    tvInfoError.text = infoError
                    imgContainer.setImageResource(R.drawable.ic_warning)
                    btnConfirm.setOnClickListener {
                        onClick.invoke()
                        dialog.dismiss()
                    }
                }
                in 500..599 -> {
                    val message = context.getString(R.string.server_error)
                    tvTitleError.text = message
                    tvInfoError.text =  infoError
                    imgContainer.setImageResource(R.drawable.ic_warning)
                    btnConfirm.setOnClickListener {
                        onClick.invoke()
                        dialog.dismiss()
                    }
                }
                else -> {
                    tvTitleError.text = titleError
                    tvInfoError.text = infoError
                    imgContainer.setImageResource(R.drawable.ic_warning)
                    btnConfirm.setOnClickListener {
                        onClick.invoke()
                        dialog.dismiss()
                    }
                }
            }
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }


    fun permissionDialog(
       onClick:(position:Int,checkText:String)->Unit
    ){
        var dialog = android.app.AlertDialog.Builder(context,R.style.BottomSheetDialogThem)
        val create = dialog.create()
        val dialogCameraBinding = DialogCameraBinding.inflate(LayoutInflater.from(context), null, false)
        create.setView(dialogCameraBinding.root)
        dialogCameraBinding.passport.isChecked = true
        dialogCameraBinding.consCamera.setOnClickListener {
            onClick.invoke(CAMERA_POS,getGroupText(dialogCameraBinding.radioGroup))
            create.dismiss()
        }
        dialogCameraBinding.consGallery.setOnClickListener {
            onClick.invoke(GALLERY_POS,getGroupText(dialogCameraBinding.radioGroup))
            create.dismiss()
        }
        dialogCameraBinding.consPdf.setOnClickListener {
            onClick.invoke(FILE_POS,getGroupText(dialogCameraBinding.radioGroup))
            create.dismiss()
        }
        dialogCameraBinding.close.setOnClickListener {
            create.dismiss()
        }

        create.show()
    }

    fun dialogDelete(
        categoryPosition:Int,
        onClick: (isBool:Boolean) -> Unit
    ){
        var alertDialog = AlertDialog.Builder(context,R.style.BottomSheetDialogThem)
        val create = alertDialog.create()
        val dialogEditeBinding = DialogEditeBinding.inflate(layoutInflater)
        create.setView(dialogEditeBinding.root)
        when(categoryPosition){
            in 1..2->{
                /** Apiga borib malumotni muvafaqqiyatli saqlagach usha malumotlarni uchirish tug'risida**/
                dialogEditeBinding.buttonDelete.textApp(context.getString(R.string.delete_all_data))
            }
        }

        dialogEditeBinding.buttonDelete.setOnClickListener {
            onClick.invoke(true)
            create.dismiss()
        }

        dialogEditeBinding.buttonNoDelete.setOnClickListener {
            onClick.invoke(false)
            create.dismiss()
        }
        create.show()
    }


    fun dialogUpdate(
        onClick: (isBool:Boolean) -> Unit
    ){
        var alertDialog = AlertDialog.Builder(context,R.style.BottomSheetDialogThem)
        val create = alertDialog.create()
        val dialogEditeBinding = DialogEditeBinding.inflate(layoutInflater)
        create.setView(dialogEditeBinding.root)
        dialogEditeBinding.animationLottie.setAnimation(R.raw.edite)
        dialogEditeBinding.textTitle.textApp(context.getString(R.string.update_text))
        dialogEditeBinding.buttonDelete.textApp(context.getString(R.string.update))
        dialogEditeBinding.buttonDelete.setOnClickListener {
            onClick.invoke(true)
            create.dismiss()
        }
        dialogEditeBinding.buttonNoDelete.setOnClickListener {
            onClick.invoke(false)
            create.dismiss()
        }
        create.show()
    }


    fun getGroupText(radioGroup: RadioGroup):String{
        return when(radioGroup.checkedRadioButtonId){
            R.id.passport-> context.getString(R.string.passport)
            R.id.card-> context.getString(R.string.card)
            R.id.selfie-> context.getString(R.string.selfie)
            R.id.contract-> context.getString(R.string.treaty)
            R.id.product-> context.getString(R.string.product)
            R.id.signature-> context.getString(R.string.signature)
            R.id.other->context.getString(R.string.other)
            else ->  context.getString(R.string.passport)
        }
    }

}