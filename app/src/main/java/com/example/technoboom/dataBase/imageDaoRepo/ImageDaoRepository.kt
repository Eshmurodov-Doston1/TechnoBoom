package com.example.technoboom.dataBase.imageDaoRepo

import androidx.lifecycle.LiveData
import com.example.technoboom.dataBase.dao.ImageDao
import com.example.technoboom.dataBase.dao.TokenBase64Dao
import com.example.technoboom.dataBase.entity.ImageEntity
import com.example.technoboom.dataBase.entity.TokenBase64
import javax.inject.Inject

class ImageDaoRepository @Inject constructor(
    private val imageDao: ImageDao,
    private val tokenBase64Dao: TokenBase64Dao
) {
    suspend fun saveImage(imageEntity: ImageEntity){
        imageDao.insert(imageEntity)
    }

    suspend fun updateImageEntity(imageEntity: ImageEntity){
        imageDao.update(imageEntity)
    }

    suspend fun deleteImageEntity(imageEntity: ImageEntity){
        imageDao.delete(imageEntity)
    }
    suspend fun deleteTable(){
        imageDao.deleteTable()
    }

    fun getAllImageEntity():LiveData<List<ImageEntity>>{
        return imageDao.getAllImage()
    }
    fun getAllList():List<ImageEntity>{
        return imageDao.getAllList()
    }

    /** Save Token **/
    suspend fun saveToken(tokenEntity: TokenBase64){
        tokenBase64Dao.insert(tokenEntity)
    }

    suspend fun updateTokenEntity(tokenBase64:TokenBase64){
        tokenBase64Dao.update(tokenBase64)
    }

    suspend fun deleteTokenEntity(tokenEntity: TokenBase64){
        tokenBase64Dao.delete(tokenEntity)
    }
    suspend fun deleteTableToken(){
        tokenBase64Dao.deleteTable()
    }

    fun getIdToken(id:Long):TokenBase64{
        return tokenBase64Dao.getDataBase64(id)
    }
    /** Save Token **/

}