package com.example.technoboom.utils

import androidx.navigation.NavController
import com.example.technoboom.R

class ScreenNavigator(
    private val navController: NavController
) {
    fun createMainScreen(){
        navController.navigate(R.id.mainFragment)
    }
}