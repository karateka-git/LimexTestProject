package com.example.limextestproject.ui.main.channelGroup.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.limextestproject.data.models.Channels
import com.example.limextestproject.databinding.ViewHolderChannelBinding

class ChannelsViewHolder(private val binding: ViewHolderChannelBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(item: Channels) {
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
