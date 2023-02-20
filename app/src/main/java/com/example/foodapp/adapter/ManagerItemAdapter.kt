package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.model.Item
import com.example.foodapp.presenter.ManagerItemPresenter
import kotlinx.android.synthetic.main.item_product.view.*

class ManagerItemAdapter(private val context: Context, private val listItem: ArrayList<Item>, private val managerItemPresenter: ManagerItemPresenter) :
    RecyclerView.Adapter<ManagerItemAdapter.ManagerItemViewHolder>() {
    inner class ManagerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(item: Item) {
            Glide.with(context).load(item.imgUrl).into(itemView.imgItem)
            itemView.tvItemName.text = item.name
            itemView.tvItemPrice.text = item.price.toString()
            itemView.setOnClickListener {
                managerItemPresenter.update(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerItemViewHolder {
        return ManagerItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ManagerItemViewHolder, position: Int) {
        holder.bindData(listItem[position])
    }
}