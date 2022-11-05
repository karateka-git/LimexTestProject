package com.example.limextestproject.ui.main.channelGroup.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.limextestproject.data.models.Channel
import com.example.limextestproject.databinding.ViewHolderChannelBinding

class ChannelsViewHolder(
    private val binding: ViewHolderChannelBinding,
    private val favoriteOnClickListener: (Channel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var currentItem: Channel? = null

    init {
        binding.favoriteIcon.setOnClickListener {
            currentItem?.let {
                favoriteOnClickListener(it)
            }
        }
    }

    fun bindTo(item: Channel) {
        currentItem = item
        binding.apply {
            Glide.with(root.context)
                .load(item.image)
                .into(image)
            title.text = item.name
            currentProgram.text = item.currentProgram.title
            favoriteIcon.isSelected = item.isFavorite
        }
    }
}
