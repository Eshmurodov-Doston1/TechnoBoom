package com.example.technoboom.dataBase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.technoboom.dataBase.entity.ImageEntity
@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ImageEntity)

    @Update
    suspend fun update(entity: ImageEntity)

    @Delete
    suspend fun delete(entity: ImageEntity)

    @Query("delete from  ImageEntity")
    suspend fun deleteTable()

    @Query("SELECT*FROM ImageEntity")
    fun getAllImage():LiveData<List<ImageEntity>>

    @Query("SELECT*FROM ImageEntity")
    fun getAllList():List<ImageEntity>
}