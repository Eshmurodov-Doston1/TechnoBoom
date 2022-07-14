package com.example.technoboom.presentation.pages.basePage

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseFragment(
    @LayoutRes var layoutRes: Int
    ):Fragment(layoutRes),CoroutineScope {


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate + Job()

}