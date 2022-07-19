package com.example.technoboom.network.authService

import com.example.technoboom.models.AuthData
import com.example.technoboom.models.files.SendData
import com.example.technoboom.utils.ResponseFetcher
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val responseFetcher: ResponseFetcher.Base
){
    suspend fun authData(token:String,authData: AuthData) = responseFetcher.getFlowResponseState(authService.authData(token,authData))

    suspend fun getFiles(token:String,sendData: SendData) = responseFetcher.getFlowResponseState(authService.getFiles(token,sendData))
}