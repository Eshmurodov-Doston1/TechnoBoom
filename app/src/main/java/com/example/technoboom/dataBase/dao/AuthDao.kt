package com.example.technoboom.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.technoboom.dataBase.entity.AuthEntity

@Dao
interface AuthDao{
    @Query("SELECT*FROM authentity")
    fun getAuth():AuthEntity
}