package com.example.technoboom.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.technoboom.BuildConfig
import com.example.technoboom.R
import com.permissionx.guolindev.PermissionX

class AppCompositionRoot(
    private val context: Context,
    private val activity: AppCompatActivity,
    private val navController: NavController,
    private val uiController:UiController
) {
    private val mLayoutInflater: LayoutInflater get() = activity.layoutInflater
    val mActivity: AppCompatActivity get() = activity
    private val dialogHelper:DialogHelper by lazy {
        DialogHelper(mContext,mLayoutInflater,activity)
    }

    val mLifecycleOwner: LifecycleOwner get() = activity
    val mContext: Context get() = activity
    val mNavController: NavController get() = navController
    val screenNavigator:ScreenNavigator by lazy {
        ScreenNavigator(navController)
    }
    val uiControllerApp get() = uiController


    fun errorDialog(
        title: String,
        info: String,
        code: Int,
        onClick: () -> Unit
    ) {
        dialogHelper.errorDialog(title, info, code) {
            onClick.invoke()
        }
    }



    fun deleteDialog(
        categoryPosition:Int,
        onClick: (isClick:Boolean) -> Unit
    ){
        dialogHelper.dialogDelete(categoryPosition,onClick)
    }


    fun updateDialog(onClick: (isClick:Boolean) -> Unit){
        dialogHelper.dialogUpdate(onClick)
    }

    fun permissionDialog(
        onClick: (position:Int,categoryText:String) -> Unit
    ){
        PermissionX.init(activity)
            .permissions(Manifest.permission.CAMERA)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, mActivity.getString(R.string.no_help), "OK", mActivity.getString(R.string.cancel))
            }.request { allGranted, grantedList, deniedList ->
                if (allGranted){
                    dialogHelper.permissionDialog(onClick)
                } else {
                   mActivity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)))
                }
            }
    }
}