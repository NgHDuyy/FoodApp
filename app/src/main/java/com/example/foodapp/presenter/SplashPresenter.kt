package com.example.foodapp.presenter

import android.content.Context
import android.content.Intent
import com.example.foodapp.ui.MainActivity
import com.example.foodapp.MySharePreference
import com.example.foodapp.ui.login.LoginActivity

class SplashPresenter {
    private var IS_LOGGED: String = "IS_LOGGED"
    fun checkLogin(context: Context) {
        val intent: Intent = if (!MySharePreference().getInstance(context)!!.getBooleanLoginValue(IS_LOGGED)) {
            Intent(context, LoginActivity::class.java)
        } else {
            Intent(context, MainActivity::class.java)
        }
        context.startActivity(intent)
    }
}