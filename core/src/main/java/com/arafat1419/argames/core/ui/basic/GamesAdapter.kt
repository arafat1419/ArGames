package com.arafat1419.argames.core.ui.basic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arafat1419.argames.assets.R
import com.arafat1419.argames.core.databinding.ItemGameLayoutBinding
import com.arafat1419.argames.core.domain.model.GameDomain
import com.bumptech.glide.Glide

class GamesAdapter : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    private var listData = ArrayList<GameDomain>()
    private var favoriteGamesId = ArrayList<Int>()

    var onItemClicked: ((game: GameDomain) -> Unit)? = null
    var onFavoriteClicked: ((game: GameDomain, isFavorite: Boolean) -> Unit)? = null

    fun setData(newListData: List<GameDomain>?, newFavoriteGamesId: List<Int>) {
        if (newListData == null) return
        listData.clear()
        favoriteGamesId.clear()
        listData.addAll(newListData)
        favoriteGamesId.addAll(newFavoriteGamesId)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemsDataBinding =
            ItemGameLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(private val binding: ItemGameLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GameDomain) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.backgroundImage)
                    .into(imgBackground)

                txtTitle.text = data.name
                txtRelease.text = data.released
                txtRating.text = data.rating

                val isFavorite = (favoriteGamesId.firstOrNull { it == data.id }) != null

                btnFavorite.apply {
                    if (isFavorite) {
                        setImageDrawable(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_favorite_24
                            )
                        )
                        setOnClickListener {
                            onFavoriteClicked?.invoke(data,true)
                        }
                    } else {
                        setImageDrawable(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_favorite_border_24
                            )
                        )
                        setOnClickListener {
                            onFavoriteClicked?.invoke(data,false)
                        }
                    }
                }

                itemView.setOnClickListener {
                    onItemClicked?.invoke(data)
                }
            }
        }
    }
}