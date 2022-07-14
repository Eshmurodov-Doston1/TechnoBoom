package com.example.technoboom.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TokenBase64(
    @PrimaryKey
    var id:Long,
    var base64Data:String
)
