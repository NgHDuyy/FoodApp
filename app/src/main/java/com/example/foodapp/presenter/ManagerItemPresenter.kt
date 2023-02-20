package com.example.foodapp.presenter

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.interfaces.ManagerItemInterface
import com.example.foodapp.model.Item
import com.example.foodapp.ui.manager.item.UpdateItem
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.layout_confirm_delete_item.*
import kotlinx.android.synthetic.main.layout_update_item.*

class ManagerItemPresenter(
    private val context: Context,
    private val managerItemInterface: ManagerItemInterface
) {

    private val database = FirebaseDatabase.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val listItems: ArrayList<Item> = arrayListOf()

    fun getItems() {
        database.getReference("items").orderByChild("type")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listItems.clear()
                    if (snapshot.exists()) {
                        for (photoSnap in snapshot.children) {
                            listItems.add(photoSnap.getValue(Item::class.java)!!)
                        }
                        managerItemInterface.setLayout()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun getListItem(): ArrayList<Item> {
        return listItems
    }

    fun update(item: Item) {
        val dialog = Dialog(context)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_update_item)

        val window = dialog.window
        window.run {
            if (this == null) {
                return
            }
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.BOTTOM)
        }

        dialog.setCancelable(false)
        Glide.with(context).load(item.imgUrl).into(dialog.imgItem)
        dialog.edtItemName.text = item.name
        dialog.edtItemPrice.text = item.price.toString()

        dialog.cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.delete.setOnClickListener {
            val dialog1 = Dialog(context)
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1.setContentView(R.layout.layout_confirm_delete_item)

            val window1 = dialog1.window
            window1.run {
                if (this == null) {
                    return@run
                }
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setGravity(Gravity.BOTTOM)
            }
            dialog1.cancel_delete.setOnClickListener {
                dialog1.dismiss()
            }
            dialog1.confirm_delete.setOnClickListener {
                deleteItem(item)
                managerItemInterface.setLayout()
                dialog1.dismiss()
                dialog.dismiss()
            }
            dialog1.show()
        }
        dialog.update.setOnClickListener {
            val intent = Intent(context, UpdateItem::class.java)
            intent.putExtra("item", item)
            context.startActivity(intent)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun updateItem(item: Item) {
        val imageName = storage.getReference("photos")
            .child(item.name + "." + getFileExtension(item.imgUrl!!.toUri()))
        imageName.putFile(Uri.parse(item.imgUrl)).addOnSuccessListener {
            imageName.downloadUrl.addOnSuccessListener { uri ->
                item.imgUrl = uri.toString()
                database.getReference("items").child(item.id.toString())
                    .updateChildren(item.toMap())
                managerItemInterface.success()
            }
        }
    }


    private fun getFileExtension(imgUri: Uri): String {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(imgUri))
            .toString()
    }

    private fun deleteItem(item: Item) {
        database.getReference("items").child(item.id.toString()).removeValue()
    }

    fun addItem(item: Item) {
        val imageName = storage.getReference("photos")
            .child(item.name + "." + getFileExtension(item.imgUrl!!.toUri()))
        imageName.putFile(Uri.parse(item.imgUrl)).addOnSuccessListener {
            imageName.downloadUrl.addOnSuccessListener { uri ->
                item.imgUrl = uri.toString()
                database.getReference("items").child(item.id.toString())
                    .setValue(item).addOnCompleteListener {
                        managerItemInterface.success()
                    }
            }
        }
    }

}