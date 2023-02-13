package com.example.foodapp.model

import java.io.Serializable

class Bill(
    var name: String? = null,
    var phone: String? = null,
    var address: String? = null,
    var note: String? = null,
    var listItemBill: ArrayList<ItemBill> = arrayListOf(),
    var sum: Long? = 0,
    var time: String? = null
) : Serializable