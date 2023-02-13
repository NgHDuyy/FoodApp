package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.model.Item
import com.example.foodapp.presenter.HomePresenter
import kotlinx.android.synthetic.main.item_product.view.*

class ItemAdapter(private val context: Context, private val items: ArrayList<Item>, private val homePresenter: HomePresenter) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bindData(item: Item, homePresenter: HomePresenter) {
            Glide.with(context).load(item.imgUrl).into(itemView.imgItem)
            itemView.tvItemName.text = item.name
            itemView.tvItemPrice.text = item.price.toString()
            itemView.setOnClickListener {
                homePresenter.showDialogAddToCart(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(items[position], homePresenter)
    }

    override fun getItemCount(): Int = items.size
}