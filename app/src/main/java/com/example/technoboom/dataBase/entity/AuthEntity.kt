package com.example.technoboom.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0,
    var keyData:String
)
