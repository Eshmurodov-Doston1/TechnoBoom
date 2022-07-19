package com.example.technoboom.di

import com.example.technoboom.BuildConfig
import com.example.technoboom.BuildConfig.BASE_URL
import com.example.technoboom.network.authService.AuthService
import com.google.common.net.HttpHeaders
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providesBaseUrl():String{
        return if (BuildConfig.DEBUG){
            BASE_URL
        }else{
            BASE_URL
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

       var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10000, TimeUnit.SECONDS)
            .readTimeout(10000, TimeUnit.SECONDS)


        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(baseUrl:String,okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthService(retrofit: Retrofit):AuthService = retrofit.create(AuthService::class.java)

}