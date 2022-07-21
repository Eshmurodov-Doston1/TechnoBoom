package com.example.technoboom.presentation.pages.auth


import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import com.example.technoboom.MainActivity
import com.example.technoboom.R
import com.example.technoboom.databinding.FragmentAuthBinding
import com.example.technoboom.presentation.pages.basePage.BaseFragment
import com.example.technoboom.utils.AppCompositionRoot
import com.example.technoboom.utils.isNotNullOrEmpty


class AuthFragment : BaseFragment(R.layout.fragment_auth) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (authVm.sharedPreference.login.isNotNullOrEmpty() && authVm.sharedPreference.orderNumber.isNotNullOrEmpty()){
            appCompositionRoot.screenNavigator.createApplicationMainScreen()
        }else if(authVm.sharedPreference.login.isNotNullOrEmpty() && !authVm.sharedPreference.orderNumber.isNotNullOrEmpty()){
            appCompositionRoot.screenNavigator.createMainScreenApp()
        }
    }
    private val binding:FragmentAuthBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginBtn.setOnClickListener {
                val loginStr = login.text.toString().trim()
                val passwordStr = password.text.toString().trim()
                if (!loginStr.isNotNullOrEmpty()){
                    appCompositionRoot.errorDialog(requireActivity().getString(R.string.no_login),"",-2){
                        login.requestFocus()
                    }
                }else{
                    authVm.sharedPreference.login = loginStr
                    authVm.sharedPreference.password = passwordStr
                    appCompositionRoot.screenNavigator.createMainScreenApp()
                }
            }
        }
    }
}