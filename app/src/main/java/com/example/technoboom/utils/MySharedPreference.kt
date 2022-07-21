package com.example.technoboom.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.technoboom.utils.AppConstant.COMPANYNAME
import com.example.technoboom.utils.AppConstant.EMPTY_STRING
import com.example.technoboom.utils.AppConstant.IMAGE_DATA
import com.example.technoboom.utils.AppConstant.LOGIN
import com.example.technoboom.utils.AppConstant.ORDER_NUMBER
import com.example.technoboom.utils.AppConstant.PASSWORD
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

    var login:String?
        get() = preferences.getString(LOGIN, EMPTY_STRING)
        set(value) = preferences.edit{
            if (value!=null){
                it.putString(LOGIN,value)
            }
        }
    var password:String?
        get() = preferences.getString(PASSWORD, EMPTY_STRING)
        set(value) = preferences.edit{
            if (value!=null){
                it.putString(PASSWORD,value)
            }
        }
}