package com.example.technoboom.di

import android.content.Context
import android.media.Image
import androidx.room.Room
import com.example.technoboom.dataBase.AppDataBase
import com.example.technoboom.dataBase.dao.AuthDao
import com.example.technoboom.dataBase.dao.ImageDao
import com.example.technoboom.dataBase.dao.TokenBase64Dao
import com.example.technoboom.dataBase.entity.TokenBase64
import com.example.technoboom.utils.AppConstant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context,AppDataBase::class.java,DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideAuthDao(dataBase: AppDataBase):AuthDao = dataBase.authDao()

    @Provides
    @Singleton
    fun provideImageDao(dataBase: AppDataBase):ImageDao = dataBase.imageDao()
    @Provides
    @Singleton
    fun provideTokenBase64(dataBase: AppDataBase):TokenBase64Dao = dataBase.tokenBase64()
}