package com.example.technoboom.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.technoboom.utils.AppConstant.COMPANYNAME
import com.example.technoboom.utils.AppConstant.EMPTY_STRING
import com.example.technoboom.utils.AppConstant.IMAGE_DATA
import com.example.technoboom.utils.AppConstant.ORDER_NUMBER
import com.example.technoboom.utils.AppConstant.PINFL
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/** this is class SharedPreference create and save storage data accessToken and refreshToken and token type and others **/

class MySharedPreference @Inject constructor(@ApplicationContext private val context: Context){
    private val NAME = COMPANYNAME
    private val MODE = Context.MODE_PRIVATE
    private val preferences: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    /** clear sharedPreference **/
    fun clear(){
        var edite = preferences.edit()
//        preferences.edit().remove(PINFL)
//        preferences.edit().remove(ORDER_NUMBER)
//        preferences.edit().remove(IMAGE_DATA)
        edite.clear()
        edite.apply()
    }


    var orderNumber:String?
        get()= preferences.getString(ORDER_NUMBER, EMPTY_STRING)
        set(value) = preferences.edit{
            if (value!=null){
                it.putString(ORDER_NUMBER,value)
            }
        }



    var pinfl:String?
    get()= preferences.getString(PINFL, EMPTY_STRING)
    set(value) = preferences.edit{
        if (value!=null){
            it.putString(PINFL,value)
        }
    }


    var imageData:String?
    get() = preferences.getString(IMAGE_DATA, EMPTY_STRING)
    set(value) = preferences.edit{
        if (value!=null){
            it.putString(IMAGE_DATA,value)
        }
    }

//    /** passwordApp **/
//    var passwordApp: String?
//        get() = preferences.getString(PASSWORDAPP, EMPTYTEXT)
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putString(PASSWORDAPP, value)
//            }
//        }

    /** passwordApp **/

//    /** save accessToken **/
//    var accessToken: String?
//        get() = preferences.getString(ACCESSTOKEN, EMPTYTEXT)
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putString(ACCESSTOKEN, value)
//            }
//        }
//    /** save refreshToken **/
//    var refreshToken: String?
//        get() = preferences.getString(REFRESHTOKEN, EMPTYTEXT)
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putString(REFRESHTOKEN, value)
//            }
//        }
//    /** save tokenType **/
//    var tokenType: String?
//        get() = preferences.getString(TOKENTYPE, EMPTYTEXT)
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putString(TOKENTYPE, value)
//            }
//        }
//
//    /** User Data **/
//    var userData: String?
//        get() = preferences.getString(USERDATA, EMPTYTEXT)
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putString(USERDATA, value)
//            }
//        }
//    /** User Data **/
//
//    /** User Data **/
//    var oldToken: String?
//        get() = preferences.getString(OLDTOKEN, EMPTYTEXT)
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putString(OLDTOKEN, value)
//            }
//        }
//    /** User Data **/
//
//
//    var language: String?
//        get() = preferences.getString(LANGUAGE, "uz")
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putString(LANGUAGE, value)
//            }
//        }
//
//
//    var theme: Boolean?
//        get() = preferences.getBoolean(THEME, false)
//        set(value) = preferences.edit {
//            if (value != null) {
//                it.putBoolean(THEME, value)
//            }
//        }
//
//
//
//    var theme_color: String?
//        get() = preferences.getString(THEMECOLOR, EMPTYTEXT)
//        set(value) = preferences.edit {
//            if (value != null) { it.putString(THEMECOLOR, value) }
//        }
}