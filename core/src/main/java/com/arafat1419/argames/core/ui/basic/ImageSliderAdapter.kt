package com.arafat1419.argames.core.ui.basic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arafat1419.argames.core.databinding.ItemSliderLayoutBinding
import com.bumptech.glide.Glide

class ImageSliderAdapter(private val images: List<String>) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemSliderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = images.size

    inner class ImageViewHolder(private val binding: ItemSliderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(images[position])
                    .into(imgBackground)

                txtCountScreenshot.text = "${position + 1}/${images.size}"
            }
        }
    }
}