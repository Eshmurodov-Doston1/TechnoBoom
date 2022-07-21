package com.example.technoboom.presentation.pages.user_data

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.viewbinding.library.fragment.viewBinding
import androidx.core.widget.doAfterTextChanged
import com.example.technoboom.R
import com.example.technoboom.databinding.FragmentUserBinding
import com.example.technoboom.presentation.pages.basePage.BaseFragment
import com.example.technoboom.utils.gone
import com.example.technoboom.utils.isNotNullOrEmpty
import com.example.technoboom.utils.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFragment : BaseFragment(R.layout.fragment_user) {
    private val binding:FragmentUserBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            pinfl.doAfterTextChanged {
                if (it.toString().trim().length==14){
                    buttonSave.isEnabled = true
                }
            }
            buttonSave.setOnClickListener {
                val pinfl = pinfl.text.toString().trim()
                val orderNumber = orderNumber.text.toString().trim()
                if (pinfl.isNotNullOrEmpty() && orderNumber.isNotNullOrEmpty()){
                        val savePinflAndOrderNumber = authVm.savePinflAndOrderNumber(pinfl, orderNumber)
                        if (savePinflAndOrderNumber){
                                appCompositionRoot.screenNavigator.createApplicationMainScreen()
                        }
                }else{
                    appCompositionRoot.errorDialog(
                        appCompositionRoot.mActivity.getString(R.string.error_input),
                        appCompositionRoot.mActivity.getString(R.string.empty_pinfl_or_order_number),
                        -2){
                        // TODO: Clicked button dialog
                    }
                }
            }
        }
    }
}