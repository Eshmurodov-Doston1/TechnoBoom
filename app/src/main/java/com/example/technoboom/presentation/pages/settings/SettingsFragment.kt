package com.example.technoboom.presentation.pages.settings


import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.technoboom.R
import com.example.technoboom.databinding.FragmentSettingsBinding
import com.example.technoboom.presentation.pages.basePage.BaseFragment
import com.example.technoboom.utils.enabledTrue


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val binding:FragmentSettingsBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            login.setText(authVm.sharedPreference.login)
            password.setText(authVm.sharedPreference.password)
            orderNumber.setText(authVm.sharedPreference.orderNumber)
            pinfl.setText(authVm.sharedPreference.pinfl)
            update.enabledTrue()
            update.setOnClickListener {
                val login = login.text.toString().trim()
                val password = password.text.toString().trim()
                val orderNumber = orderNumber.text.toString().trim()
                val pinfl = pinfl.text.toString().trim()
                appCompositionRoot.updateDialog {
                    if(it){
                        authVm.sharedPreference.login = login
                        authVm.sharedPreference.password = password
                        authVm.sharedPreference.orderNumber = orderNumber
                        authVm.sharedPreference.pinfl = pinfl
                        appCompositionRoot.screenNavigator.popBackStack()
                    }
                }
            }
            logout.setOnClickListener {
                authVm.sharedPreference.clear()
                appCompositionRoot.mActivity.recreate()
                val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.authFragment, true).build()
                findNavController().navigate(R.id.authFragment, null, navOptions)

            }
        }
    }
}