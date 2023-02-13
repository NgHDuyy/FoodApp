package com.example.foodapp.presenter

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.interfaces.HomeInterface
import com.example.foodapp.model.Item
import com.example.foodapp.model.ItemBill
import com.example.foodapp.model.Photo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.layout_dialog_add_to_cart.*

class HomePresenter(
    val context: Context,
    private val homeInterface: HomeInterface
) {
    private val cartPresenter: CartPresenter = CartPresenter(context)
    private val database = FirebaseDatabase.getInstance()

    private val listFavoritePhotos: ArrayList<Photo> = arrayListOf()
    private val listItems: ArrayList<Item> = arrayListOf()

    init {
        getItems()
        getFavoritePhoto()
    }

    private fun getFavoritePhoto() {
        database.getReference("favoriteFoodImage")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listFavoritePhotos.clear()
                    if (snapshot.exists()) {
                        for (photoSnap in snapshot.children) {
                            listFavoritePhotos.add(photoSnap.getValue(Photo::class.java)!!)
                        }
                        homeInterface.setLayoutSlideImage()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun getItems() {
        database.getReference("items").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listItems.clear()
                if (snapshot.exists()) {
                    for (photoSnap in snapshot.children) {
                        listItems.add(photoSnap.getValue(Item::class.java)!!)
                    }
                    homeInterface.setLayoutItem()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun getListFavoritePhoto(): ArrayList<Photo> {
        return listFavoritePhotos
    }

    fun getListItem(): ArrayList<Item> {
        return listItems
    }

    fun showDialogAddToCart(item: Item) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog_add_to_cart)

        val window = dialog.window
        window.run {
            if (this == null) {
                return
            }
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setGravity(Gravity.BOTTOM)
        }

        dialog.setCancelable(false)

        Glide.with(context).load(item.imgUrl).into(dialog.imgItem)
        dialog.name.text = item.name
        dialog.price.text = item.price.toString()
        dialog.tvCount.text = "1"

        var count = (dialog.tvCount.text as String).toInt()
        val price = (dialog.price.text as String).toInt()

        dialog.sub.setOnClickListener {
            if (count > 0){
                count --
                dialog.tvCount.text = count.toString()
                dialog.price.text = (price * count).toString()
            }
        }

        dialog.add.setOnClickListener{
            count ++
            dialog.tvCount.text = count.toString()
            dialog.price.text = (price * count).toString()
        }

        dialog.cancel.setOnClickListener{
            dialog.dismiss()
        }

        dialog.clickAddToCart.setOnClickListener {
            val itemBill = ItemBill(null,dialog.name.text.toString(),(dialog.price.text.toString()).toInt(), (dialog.tvCount.text.toString()).toInt(),item.imgUrl.toString())
            cartPresenter.addItemBill(itemBill)
            dialog.dismiss()
        }

        dialog.show()
    }
}