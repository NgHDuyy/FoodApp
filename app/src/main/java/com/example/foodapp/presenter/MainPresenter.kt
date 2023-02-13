package com.example.foodapp.presenter

import android.content.Context
import android.content.Intent
import com.example.foodapp.MySharePreference
import com.example.foodapp.interfaces.MainInterface
import com.example.foodapp.model.User
import com.example.foodapp.ui.login.LoginActivity
import com.google.gson.Gson

class MainPresenter(var mainInterface: MainInterface, var context: Context) {

    private val sharePre = MySharePreference()
    private var USER_LOGGED: String = "USER_LOGGED"
    private var IS_LOGGED: String = "IS_LOGGED"

    fun getUserInfo() {
        val user = Gson().fromJson(
            sharePre.getInstance(context)!!.getUserLogged(USER_LOGGED),
            User::class.java
        )
        mainInterface.setUserInfo(user)
    }

    fun logout() {
        sharePre.getInstance(context)!!.putBooleanLoginValue(IS_LOGGED, false)
        mainInterface.logout()
    }

    fun nextLoginActivity() {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}