package com.example.technoboom.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.technoboom.dataBase.entity.ImageEntity
import com.example.technoboom.dataBase.entity.TokenBase64
import com.example.technoboom.dataBase.imageDaoRepo.ImageDaoRepository
import com.example.technoboom.models.AuthData
import com.example.technoboom.network.authService.AuthRepository
import com.example.technoboom.utils.AppConstant.NOCONNECTION
import com.example.technoboom.utils.MySharedPreference
import com.example.technoboom.utils.NetworkHelper
import com.example.technoboom.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.IOException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class AuthVm @Inject constructor(
    private val authRepository: AuthRepository,
    private val networkHelper:NetworkHelper,
    private val mySharedPreference: MySharedPreference,
    private val imageDaoRepository: ImageDaoRepository
) :ViewModel(){
    val sharedPreference:MySharedPreference = mySharedPreference

    val authResponse:StateFlow<ResponseState<ResponseBody?>> get() = _authResponse
    private var _authResponse = MutableStateFlow<ResponseState<ResponseBody?>>(ResponseState.Loading)


    fun auth(authData: AuthData){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                try {
                    _authResponse.emit(ResponseState.Loading)
                    val credentials: String = "Тест" + ":"
                    val basic = "Basic " + Base64.getEncoder().encodeToString(credentials.toByteArray())
                    authRepository.authData(basic,authData)
                        .catch {data->
                            _authResponse.emit(ResponseState.Error(data.hashCode(),data.message))
                        }
                        .collect{ response->
                            _authResponse.emit(response)
                        }
                }catch (e:IOException){
                    _authResponse.emit(ResponseState.Error(e.hashCode(),e.message))
                }
            }else{
                _authResponse.emit(ResponseState.Error(NOCONNECTION))
            }
        }
    }

    fun savePinflAndOrderNumber(pinfl:String,orderNumber:String):Boolean{
       return try {
           mySharedPreference.orderNumber = orderNumber
           mySharedPreference.pinfl = pinfl
           return true
       }catch (e:Exception){
           return false
       }
    }


    suspend fun saveImageEntity(listImageEntity:ImageEntity){
        try {
            imageDaoRepository.saveImage(listImageEntity)
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteImageEntity(imageEntity: ImageEntity){
        imageDaoRepository.deleteImageEntity(imageEntity)
    }

    suspend fun updateImageEntity(imageEntity: ImageEntity){
        imageDaoRepository.updateImageEntity(imageEntity)
    }

    fun getAllImageEntity():LiveData<List<ImageEntity>>{
        return imageDaoRepository.getAllImageEntity()
    }

    fun getAllList():List<ImageEntity>{
        return imageDaoRepository.getAllList()
    }

    suspend fun deleteAllDataTableImage() = imageDaoRepository.deleteTable()



    fun getIdToken(id:Long):TokenBase64{
        return imageDaoRepository.getIdToken(id)
    }

    suspend fun saveTokenBase64(tokenBase64: TokenBase64){
        imageDaoRepository.saveToken(tokenBase64)
    }


}