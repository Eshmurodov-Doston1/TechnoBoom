package com.example.technoboom.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.technoboom.dataBase.dao.AuthDao
import com.example.technoboom.dataBase.dao.BaseDao
import com.example.technoboom.dataBase.dao.ImageDao
import com.example.technoboom.dataBase.dao.TokenBase64Dao
import com.example.technoboom.dataBase.entity.AuthEntity
import com.example.technoboom.dataBase.entity.ImageEntity
import com.example.technoboom.dataBase.entity.TokenBase64

@Database(entities = [AuthEntity::class,ImageEntity::class,TokenBase64::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun authDao():AuthDao
    abstract fun imageDao():ImageDao
    abstract fun tokenBase64():TokenBase64Dao

}