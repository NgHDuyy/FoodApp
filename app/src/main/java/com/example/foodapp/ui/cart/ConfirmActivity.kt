package com.example.foodapp.ui.cart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.ItemBillAdapter
import com.example.foodapp.presenter.CartPresenter
import kotlinx.android.synthetic.main.activity_confirm.*
import java.text.SimpleDateFormat
import java.util.*

class ConfirmActivity : AppCompatActivity() {

    private lateinit var name: String

    private lateinit var cartPresenter: CartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        cartPresenter = CartPresenter(applicationContext)
        name = intent.getStringExtra("userName").toString()

        setLayout()

        cancel.setOnClickListener {
            finish()
        }

        confirm.setOnClickListener {
            if (phone.text.toString().equals("")) {
                Toast.makeText(this, "Nhập số điện thoại!", Toast.LENGTH_SHORT).show()
            } else if (address.text.toString().equals("")) {
                Toast.makeText(this, "Nhập địa chỉ!", Toast.LENGTH_SHORT).show()
            } else {
                cartPresenter.pay(phone.text.toString(), address.text.toString(), note.text.toString())
                finish()
            }
        }

    }

    private fun setLayout() {
        userName.text = name
        sum.text = intent.getLongExtra("sum", 0).toString()
        rcv_bill.layoutManager = LinearLayoutManager(this)
        rcv_bill.adapter =
            ItemBillAdapter(this, cartPresenter.getListItemBill(), cartPresenter, false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}