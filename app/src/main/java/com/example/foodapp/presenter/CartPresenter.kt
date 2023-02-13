package com.example.foodapp.presenter

import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.example.foodapp.MySharePreference
import com.example.foodapp.dataBase.ItemBillDataBase
import com.example.foodapp.interfaces.HistoryInterface
import com.example.foodapp.model.Bill
import com.example.foodapp.model.ItemBill
import com.example.foodapp.model.User
import com.example.foodapp.ui.cart.ConfirmActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CartPresenter(val context: Context) : HistoryInterface {

    private var sum: Long = 0

    private val db = Room.databaseBuilder(
        context,
        ItemBillDataBase::class.java, "itemBills"
    ).allowMainThreadQueries().build()

    fun getListItemBill(): ArrayList<ItemBill> {
        val listItemBill = db.itemBillDao().getAllItemBills() as ArrayList<ItemBill>
        sum = 0
        for (i in 0 until listItemBill.size) {
            sum += listItemBill[i].price!!.toLong()
        }
        return listItemBill
    }

    fun addItemBill(itemBill: ItemBill) {
        db.itemBillDao().insertItemBill(itemBill)
    }

    fun deleteItemBill(itemBill: ItemBill) {
        db.itemBillDao().delete(itemBill)
    }

    fun deleteAll() {
        db.itemBillDao().deleteAll()
    }

    fun getSum(): Long {
        return sum
    }


    private val sharePre = MySharePreference()
    private var USER_LOGGED: String = "USER_LOGGED"
    private val user = Gson().fromJson(
        sharePre.getInstance(context)!!.getUserLogged(USER_LOGGED),
        User::class.java
    )

    fun confirm() {
        val intent = Intent(context, ConfirmActivity::class.java)
        intent.putExtra("userName", user.userName)
        intent.putExtra("sum", sum)
        context.startActivity(intent)
    }

    fun pay(phone: String, address: String, note: String) {
        val time =
            SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().time).toString()
        val bill = Bill(user.userName, phone, address,note, getListItemBill(), sum, time)
        HistoryPresenter(context, this).addBill(bill)
        deleteAll()
    }

    override fun setLayout() {

    }
}