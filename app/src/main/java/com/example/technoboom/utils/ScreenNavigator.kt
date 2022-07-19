package com.example.technoboom.utils

import android.util.Log
import androidx.navigation.NavController
import com.example.technoboom.R

class ScreenNavigator(
    private val navController: NavController
) {
    fun createMainScreen(){
        navController.navigate(R.id.mainFragment)
    }
}