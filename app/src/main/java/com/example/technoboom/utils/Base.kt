package com.example.technoboom.utils

import android.telephony.TelephonyManager
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

interface ResponseFetcher {
    fun <T> getFlowResponseState(response: Response<T>): Flow<ResponseState<T?>>
    fun <T> errorMessage(response: Response<T>?): String


    class Base @Inject constructor() : ResponseFetcher{
        override fun <T> getFlowResponseState(response: Response<T>) =
            flow {
                val flow = try { coroutineScope {
                        if (response.isSuccessful) ResponseState.Success(response.body())
                        else throw HttpException(response)
                    }
                } catch (e: IOException) {
                    ResponseState.Error(e.hashCode(), e.message)
                } catch (e: HttpException) {
                    ResponseState.Error(e.code(), errorMessage(e.response()))
                } catch (e:IllegalArgumentException){
                    ResponseState.Error(e.hashCode(), e.message)
                }catch (e:TimeoutException){
                    ResponseState.Error(e.hashCode(), e.message)
                }
                catch (e: Exception) {
                    ResponseState.Error(e.hashCode(), e.message)
                }
                emit(flow)
            }.flowOn(Dispatchers.IO)


        override fun <T> errorMessage(response: Response<T>?): String {
            return try {
                val jsonObject = JSONObject(response?.errorBody()?.string()!!)
                return if (jsonObject.has("Еrror")) {
                    jsonObject.get("Еrror").toString()
                } else {
                    // TODO: need to write message body
                    ""
                }
            } catch (e: Exception) {
                // TODO: need to write message body
                e.message ?: ""
            }
        }

    }
}
