package com.example.foodapp

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.foodapp.model.User
import com.google.gson.Gson

class MySharePreference {
    private var accountUtil: MySharePreference? = null
    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun getInstance(context: Context): MySharePreference? {
        if (accountUtil == null) accountUtil = MySharePreference()
        if (pref == null) {
            pref = PreferenceManager.getDefaultSharedPreferences(context)
            accountUtil?.pref = pref
        }
        return accountUtil
    }

    fun putBooleanLoginValue(key: String, value: Boolean) {
        editor = pref?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    fun getBooleanLoginValue(key: String): Boolean {
        return pref?.getBoolean(key, false)!!
    }

    fun putUserLogged(key: String, user: User){
        val strUser = Gson().toJson(user)
        editor = pref?.edit()
        editor?.putString(key, strUser)
        editor?.apply()
    }

    fun getUserLogged(key: String): String {
        return pref?.getString(key, null).toString()
    }

}