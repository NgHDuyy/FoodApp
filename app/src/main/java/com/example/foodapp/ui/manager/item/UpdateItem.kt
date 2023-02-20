package com.example.foodapp.ui.manager.item

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.interfaces.ManagerItemInterface
import com.example.foodapp.model.Item
import com.example.foodapp.presenter.ManagerItemPresenter
import kotlinx.android.synthetic.main.activity_update_item.*
import kotlinx.android.synthetic.main.activity_update_item.edtItemName
import kotlinx.android.synthetic.main.activity_update_item.edtItemPrice
import kotlinx.android.synthetic.main.activity_update_item.imgItem

class UpdateItem : AppCompatActivity(), ManagerItemInterface {
    private val reqCode = 0
    private lateinit var strImage: Uri
    private lateinit var item: Item
    private lateinit var managerItemPresenter: ManagerItemPresenter
    private lateinit var dialog: Dialog

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                strImage = result.data!!.data!!
                imgItem.setImageURI(strImage)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        managerItemPresenter = ManagerItemPresenter(this, this)
        item = intent.getSerializableExtra("item") as Item

        Glide.with(this).load(item.imgUrl).into(imgItem)
        edtItemName.setText(item.name)
        edtItemPrice.setText(item.price.toString())
        strImage = item.imgUrl!!.toUri()

        changeImage.setOnClickListener {
            onClickRequestPermission()
        }
        update.setOnClickListener {
            val itemUpdate = Item(
                item.id,
                edtItemName.text.toString(),
                edtItemPrice.text.toString().toInt(),
                strImage.toString(),
                item.type
            )
            managerItemPresenter.updateItem(itemUpdate)
            showDialog()
        }
        back.setOnClickListener { finish() }
    }

    private fun showDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.fragment_progress_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }


    private fun onClickRequestPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            val per = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(per, reqCode)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        activityResultLauncher.launch(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == reqCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    override fun setLayout() {
    }

    override fun success() {
        dialog.dismiss()
        finish()
    }

}