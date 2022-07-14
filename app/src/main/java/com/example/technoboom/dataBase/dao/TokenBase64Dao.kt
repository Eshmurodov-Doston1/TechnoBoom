package com.example.technoboom.dataBase.dao

import androidx.room.*
import com.example.technoboom.dataBase.entity.TokenBase64

@Dao
interface TokenBase64Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TokenBase64)

    @Update
    suspend fun update(entity: TokenBase64)

    @Delete
    suspend fun delete(entity: TokenBase64)

    @Query("delete from  tokenbase64")
    suspend fun deleteTable()

    @Query("SELECT*FROM tokenbase64 where id=:id")
    fun getDataBase64(id:Long):TokenBase64
}