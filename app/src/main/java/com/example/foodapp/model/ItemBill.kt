package com.example.foodapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemBills")
data class ItemBill(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,

    var name: String? = "",
    var price: Int? = 0,
    var amount: Int? = 0,
    var imgUrl: String? = "",
) : java.io.Serializable