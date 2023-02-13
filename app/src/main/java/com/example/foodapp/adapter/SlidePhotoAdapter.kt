package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.model.Photo
import kotlinx.android.synthetic.main.slide_item_photo.view.*

class SlidePhotoAdapter(private val context: Context, private val photos: ArrayList<Photo>) :
    RecyclerView.Adapter<SlidePhotoAdapter.PhotoViewHolder>() {
    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(photo: Photo) {
            Glide.with(context).load(photo.source).into(itemView.slide_item_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(context).inflate(R.layout.slide_item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int)  {
        holder.bindData(photos[position])
    }

    override fun getItemCount(): Int = photos.size
}