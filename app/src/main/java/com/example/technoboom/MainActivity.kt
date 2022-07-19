package com.example.technoboom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.activity.viewBinding
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.technoboom.databinding.ActivityMainBinding
import com.example.technoboom.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),UiController {
    private val binding:ActivityMainBinding by viewBinding()
    lateinit var appCompositionRoot:AppCompositionRoot
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_main_app) as NavHostFragment).navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appCompositionRoot = AppCompositionRoot(this,this,navController,this)
        window.statusBarColor = ContextCompat.getColor(this,R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun showProgress() {
        binding.loading.visible()
    }

    override fun hideProgress() {
        binding.loading.gone()
    }

    override fun errors(message: String, code: Int) {
        appCompositionRoot.errorDialog(AppConstant.EMPTY_STRING, message, code){}
    }

    override fun onBackPressed() {
        if (findNavController(R.id.fragment_main_app).currentDestination?.id==R.id.mainFragment){
            super.onBackPressed()
            finish()
        }
    }
}