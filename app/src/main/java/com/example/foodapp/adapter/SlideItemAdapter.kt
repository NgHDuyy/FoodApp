package com.example.foodapp.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.model.Item
import com.example.foodapp.model.Photo
import kotlinx.android.synthetic.main.layout_item_image.view.imageView
import kotlinx.android.synthetic.main.slide_item_photo.view.*



class SlideItemAdapter(private val context: Context, private val imgSlideList: ArrayList<Item>)
    : RecyclerView.Adapter<SlideItemAdapter.SlideItemViewHolder>() {

    inner class SlideItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(item: Item) {
            Glide.with(context)
                .load(item.imgUrl)
                .into(itemView.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slide_item_photo, parent, false)
        return SlideItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imgSlideList.size
    }

    override fun onBindViewHolder(holder: SlideItemViewHolder, position: Int) {
        holder.bindData(imgSlideList[position])
    }

    fun addAllItems(newItems: List<Item>) {
        imgSlideList.addAll(newItems)
        notifyDataSetChanged()
    }
}
