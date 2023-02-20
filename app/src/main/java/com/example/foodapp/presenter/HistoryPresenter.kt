package com.example.foodapp.presenter

import android.content.Context
import android.widget.Toast
import com.example.foodapp.MySharePreference
import com.example.foodapp.interfaces.HistoryInterface
import com.example.foodapp.model.Bill
import com.example.foodapp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class HistoryPresenter(private val context: Context, private val historyInterface: HistoryInterface) {

    private val database = FirebaseDatabase.getInstance()
    private val sharePre = MySharePreference()
    private var USER_LOGGED: String = "USER_LOGGED"

    private val user = Gson().fromJson(
        sharePre.getInstance(context)!!.getUserLogged(USER_LOGGED),
        User::class.java
    )

    private val listBill: ArrayList<Bill> = arrayListOf()

    fun getBill() {
        database.getReference("bills").child(user.userName.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listBill.clear()
                    if (snapshot.exists()) {
                        for (billSnap in snapshot.children) {
                            listBill.add(billSnap.getValue(Bill::class.java)!!)
                        }
                        historyInterface.setLayout()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun getListBill(): ArrayList<Bill> {
        return listBill
    }


    fun addBill(bill: Bill) {
        database.getReference("bills").child(bill.name.toString()).push().setValue(bill)
    }
}