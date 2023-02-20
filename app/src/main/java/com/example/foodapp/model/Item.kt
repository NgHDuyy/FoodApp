package com.example.foodapp.model

import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName

@IgnoreExtraProperties
data class Item(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("price")
    var price: Int? = 0,
    @SerializedName("imgUrl")
    var imgUrl: String? = "",
    @SerializedName("type")
    var type: Int? = 0
) : java.io.Serializable {
    fun toMap(): Map<String, Any>{
        val result = HashMap<String,Any>()
        result["id"] = id!!.toInt()
        result["name"] = name.toString()
        result["price"] = price!!.toInt()
        result["imgUrl"] = imgUrl.toString()
        result["type"] = type!!.toInt()
        return result
    }
}