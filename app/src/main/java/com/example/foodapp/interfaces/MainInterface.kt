package com.example.foodapp.interfaces

import com.example.foodapp.model.User

interface MainInterface {
    fun setUserInfo(user: User)
    fun logout()
}