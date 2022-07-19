package com.example.technoboom.models.files

data class Data(
    val `file`: String,
    val fileFormat: String,
    val fileName: String,
    var isSend:Boolean?=null
)