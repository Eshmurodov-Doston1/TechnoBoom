package com.example.technoboom.presentation.pages.basePage

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.technoboom.MainActivity
import com.example.technoboom.vm.AuthVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
@AndroidEntryPoint
open class BaseFragment(
    @LayoutRes var layoutRes: Int
    ):Fragment(layoutRes),CoroutineScope {
    val authVm:AuthVm by viewModels()
    val appCompositionRoot get() = (activity as MainActivity).appCompositionRoot
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate + Job()

}