package com.example.technoboom.network.authService

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import kotlin.coroutines.CoroutineContext

object ImageUtil:CoroutineScope {
    @Throws(IllegalArgumentException::class)

    suspend fun convert(
        base64Str: String,
        resData:(image:Bitmap?)->Unit
    ) = withContext(coroutineContext){


        val decodedBytes = Base64.decode(base64Str.substring(base64Str.indexOf(",") + 1), Base64.DEFAULT)
        var imageBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
       if (imageBitmap!=null){
           resData.invoke(resizeBitmap(imageBitmap))
       }
    }



    fun convert(bitmap: Bitmap): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }


    suspend fun resizeBitmap(source: Bitmap): Bitmap {
        val maxResolution = 1000    //edit 'maxResolution' to fit your need
        val width = source.width
        val height = source.height
        var newWidth = width
        var newHeight = height
        val rate: Float

        if (width > height) {
            if (maxResolution < width) {
                rate = maxResolution / width.toFloat()
                newHeight = (height * rate).toInt()
                newWidth = maxResolution
            }
        } else {
            if (maxResolution < height) {
                rate = maxResolution / height.toFloat()
                newWidth = (width * rate).toInt()
                newHeight = maxResolution
            }
        }
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()


}