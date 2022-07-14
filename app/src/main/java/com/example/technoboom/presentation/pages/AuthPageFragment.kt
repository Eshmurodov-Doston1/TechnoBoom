package com.example.technoboom.presentation.pages

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import com.example.technoboom.MainActivity
import com.example.technoboom.R
import com.example.technoboom.databinding.FragmentAuthPageBinding
import com.example.technoboom.presentation.pages.basePage.BaseFragment
import com.example.technoboom.utils.AppConstant.EMPTY_STRING
import com.example.technoboom.utils.UiController
import com.example.technoboom.utils.fetchResult
import com.example.technoboom.vm.AuthVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthPageFragment : BaseFragment(R.layout.fragment_auth_page),UiController {
    private val binding:FragmentAuthPageBinding by viewBinding()
    private val authVm:AuthVm by viewModels()
    private val appCompositionRoot get() = (activity as MainActivity).appCompositionRoot
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {


            motionLayout.startLayoutAnimation()


            motionLayout.addTransitionListener(object:MotionLayout.TransitionListener{
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {

                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    Handler(Looper.getMainLooper()).postDelayed({
                      appCompositionRoot.screenNavigator.createMainScreen()
                    },2000)
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {

                }

            })

           // authVm.auth()
           launch {
               authVm.authResponse.fetchResult(this@AuthPageFragment){
                   Log.e("data", it?.string().toString())
               }
           }
        }
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun errors(message: String, code: Int) {
        appCompositionRoot.errorDialog(EMPTY_STRING, message, code){}
    }
}