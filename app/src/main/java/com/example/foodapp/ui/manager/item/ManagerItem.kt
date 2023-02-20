package com.example.foodapp.ui.manager.item

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.ManagerItemAdapter
import com.example.foodapp.interfaces.ManagerItemInterface
import com.example.foodapp.presenter.ManagerItemPresenter
import kotlinx.android.synthetic.main.activity_manager_item.*

class ManagerItem : AppCompatActivity(), ManagerItemInterface {

    private lateinit var managerItemPresenter: ManagerItemPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_item)

        managerItemPresenter = ManagerItemPresenter(this, this)
        managerItemPresenter.getItems()

        back.setOnClickListener {
            finish()
        }

        addItem.setOnClickListener {
            startActivity(Intent(this, AddItem::class.java))
        }
    }
    override fun setLayout() {
        rcv_item.layoutManager = GridLayoutManager(this, 2)
        rcv_item.adapter =
            ManagerItemAdapter(this, managerItemPresenter.getListItem(), managerItemPresenter)
    }

    override fun success() {

    }
}