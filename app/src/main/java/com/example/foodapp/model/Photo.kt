package com.example.foodapp.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@IgnoreExtraProperties
data class Photo(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("source")
    var source: String? = ""
) : Serializable