package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.model.ItemBill
import com.example.foodapp.presenter.CartPresenter
import kotlinx.android.synthetic.main.item_cart.view.*
import kotlinx.android.synthetic.main.item_product.view.imgItem

class ItemBillAdapter(
    private val context: Context,
    private val listItemBills: ArrayList<ItemBill>,
    private val cartPresenter: CartPresenter,
    private val flag: Boolean
) :
    RecyclerView.Adapter<ItemBillAdapter.ItemBillViewHolder>() {
    inner class ItemBillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(itemBill: ItemBill) {
            Glide.with(context).load(itemBill.imgUrl).into(itemView.imgItem)
            itemView.name.text = itemBill.name
            itemView.price.text = itemBill.price.toString()
            itemView.tvCount.text = itemBill.amount.toString()
        }
    }

    fun deleteItem(position: Int){
        listItemBills.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBillViewHolder {
        return ItemBillViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemBillViewHolder, position: Int) {
        holder.bindData(listItemBills[position])
        if (flag){
            holder.itemView.delete.visibility = View.VISIBLE
        } else {
            holder.itemView.delete.visibility = View.GONE
        }
        holder.itemView.delete.setOnClickListener {
            cartPresenter.deleteItemBill(listItemBills[position])
            deleteItem(position)
        }
    }

    override fun getItemCount(): Int = listItemBills.size
}