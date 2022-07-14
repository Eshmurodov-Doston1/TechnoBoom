package com.example.technoboom.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.coroutines.flow.StateFlow
import java.util.*

suspend fun <T> StateFlow<ResponseState<T>>.fetchResult(
    uiController: UiController,
    invokeSuccess: (T) -> Unit,
) {
    this.collect { result ->
        when (result) {
            is ResponseState.Loading -> {
                uiController.showProgress()
            }
            is ResponseState.Success -> {
                uiController.hideProgress()
                invokeSuccess.invoke(result.data!!)
            }
            is ResponseState.Error -> {
                uiController.hideProgress()
                uiController.errors(result.message ?: "", result.errorCode)
            }
        }
    }
}


fun Button.visible(){
    this.isVisible = true
}

fun Button.gone(){
    this.isVisible = false
}

fun Button.enabledTrue(){
    this.isEnabled = true
}

fun Button.enabledFalse(){
    this.isEnabled = false
}

fun String?.isNullOrEmpty():Boolean{
    return this?.trim() != null && this.trim().isNotEmpty() && this.trim() != ""
}

fun String?.isNotNullOrEmpty():Boolean{
    return this?.trim() != null && this.trim().isNotEmpty() && this.trim() != ""
}


fun View.visible(){
    this.isVisible = true
}
fun View.gone(){
    this.isVisible = false
}
fun ConstraintLayout.visible(){
    this.isVisible = true
}
fun ConstraintLayout.gone(){
    this.isVisible = false
}

fun LinearLayout.visible(){
    this.isVisible = true
}
fun LinearLayout.gone(){
    this.isVisible = false
}

fun TextView.textApp(str:String){
    this.text = str
}

fun Double.format():String{
    val formatter =  NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
    return formatter
}

fun <A:Activity> Activity.startNewActivity(activity:Class<A>){
    Intent(this,activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}