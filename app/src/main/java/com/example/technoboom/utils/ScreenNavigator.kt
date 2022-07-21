package com.example.technoboom.utils

import androidx.navigation.NavController
import com.example.technoboom.R

class ScreenNavigator(
    private val navController: NavController
) {
    fun createMainScreen(){
        navController.navigate(R.id.authFragment)
    }

    fun createMainScreenApp(){
        navController.navigate(R.id.userFragment)
    }
    fun createApplicationMainScreen(){
        navController.navigate(R.id.mainFragment)
    }

    fun createSettings(){
        navController.navigate(R.id.settingsFragment)
    }
    fun popBackStack(){
        navController.popBackStack()
    }
}