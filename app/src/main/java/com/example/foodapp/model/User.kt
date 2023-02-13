package com.example.foodapp.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class User(
    @SerializedName("id")
    var id:Int?=0,
    @SerializedName("userName")
    var userName:String?="",
    @SerializedName("account")
    var account:String?="",
    @SerializedName("password")
    var password:String?="",
    @SerializedName("isAdmin")
    var isAdmin:Boolean?=false

):Serializable