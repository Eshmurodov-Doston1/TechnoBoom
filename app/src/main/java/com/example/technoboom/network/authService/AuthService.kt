package com.example.technoboom.network.authService

import com.example.technoboom.models.AuthData
import com.example.technoboom.models.files.ResData
import com.example.technoboom.models.files.SendData
import com.google.common.net.HttpHeaders
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/UT_PreProd_5/hs/telegramBot/loadfiles")
    suspend fun authData(
        @Header(HttpHeaders.AUTHORIZATION) token:String,
        @Body authData: AuthData
    ):Response<ResponseBody>

    @POST("/UT_PreProd_5/hs/telegramBot/getfiles")
    suspend fun getFiles(
        @Header(HttpHeaders.AUTHORIZATION) token:String,
        @Body sendData: SendData
    ):Response<ResData>
}