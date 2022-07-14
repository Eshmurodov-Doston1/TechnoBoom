package com.example.technoboom.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0,
    var imagePath:String,
    var categoryImage:String
)
