package com.example.foodapp.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodapp.model.ItemBill

@Dao
interface ItemBillDAO {

    @Insert
    fun insertItemBill(itemBill: ItemBill)

    @Query("SELECT * FROM itemBills")
    fun getAllItemBills(): List<ItemBill>

    @Delete
    fun delete(itemBill: ItemBill)

    @Query("DELETE FROM itemBills")
    fun deleteAll()
}