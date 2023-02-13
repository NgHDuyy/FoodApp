package com.example.foodapp.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodapp.model.ItemBill

@Database(entities = [ItemBill::class], version = 1)
abstract class ItemBillDataBase: RoomDatabase() {
    abstract fun itemBillDao(): ItemBillDAO
}