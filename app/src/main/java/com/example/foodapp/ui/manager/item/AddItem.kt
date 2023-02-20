package com.example.foodapp.ui.manager.item

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R
import com.example.foodapp.interfaces.ManagerItemInterface
import com.example.foodapp.model.Item
import com.example.foodapp.presenter.ManagerItemPresenter
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_add_item.back
import kotlinx.android.synthetic.main.activity_add_item.changeImage

class AddItem : AppCompatActivity(), ManagerItemInterface {
    private var itemType = 0
    private val reqCode = 0
    private lateinit var strImage: Uri
    private var item: Item = Item()
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
        setContentView(R.layout.activity_add_item)

        managerItemPresenter = ManagerItemPresenter(this, this)
        managerItemPresenter.getItems()

        changeImage.setOnClickListener {
            onClickRequestPermission()
        }

        val strType = resources.getStringArray(R.array.types)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_type, strType)
        type.setAdapter(arrayAdapter)
        type.setText(arrayAdapter.getItem(0), false)
        type.setOnItemClickListener { _, _, position, _ ->
            itemType = position
        }

        add.setOnClickListener {
            if (strImage.toString() != ""){
                val itemAdd = Item(
                    item.id,
                    edtItemName.text.toString(),
                    edtItemPrice.text.toString().toInt(),
                    strImage.toString(),
                    itemType
                )
                managerItemPresenter.addItem(itemAdd)
                showDialog()
            } else {
                Toast.makeText(this, "Chọn ảnh", Toast.LENGTH_SHORT).show()
            }

        }

        back.setOnClickListener {
            finish()
        }
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
        val list = managerItemPresenter.getListItem()
        item.id =
            list[list.size - 1].id!! + 1
    }

    override fun success() {
        dialog.dismiss()
        finish()
    }
}