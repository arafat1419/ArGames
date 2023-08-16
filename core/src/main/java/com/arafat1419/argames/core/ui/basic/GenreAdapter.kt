package com.arafat1419.argames.core.ui.basic

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arafat1419.argames.core.databinding.ItemGenreLayoutBinding
import com.arafat1419.argames.core.domain.model.GenreDomain
import com.google.android.material.card.MaterialCardView
import kotlin.random.Random

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private var listData = ArrayList<GenreDomain>()

    var onItemClicked: ((genre: GenreDomain) -> Unit)? = null

    fun setData(newListData: List<GenreDomain>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemsDataBinding =
            ItemGenreLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data, position)
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(private val binding: ItemGenreLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GenreDomain, position: Int) {
            with(binding) {
                title.text = data.name
                setBackgroundTint(

                    cardGenre, when (Random.nextInt(1, 5)) {
                        0 -> {
                            android.R.color.holo_blue_light
                        }
                        1 -> {
                            android.R.color.holo_red_light
                        }
                        2 -> {
                            android.R.color.holo_orange_light
                        }
                        else -> {
                            android.R.color.holo_green_light
                        }
                    }
                )

                itemView.setOnClickListener {
                    onItemClicked?.invoke(data)
                }
            }
        }

        private fun setBackgroundTint(card: MaterialCardView, color: Int) {
            card.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(itemView.context, color)
            )
        }
    }
}