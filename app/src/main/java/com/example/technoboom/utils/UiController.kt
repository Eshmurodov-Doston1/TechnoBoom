package com.example.technoboom.utils

interface UiController {
    fun showProgress()
    fun hideProgress()
    fun errors(message: String, code: Int)
}