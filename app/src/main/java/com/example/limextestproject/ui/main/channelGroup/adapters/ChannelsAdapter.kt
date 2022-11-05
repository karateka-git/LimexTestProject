package com.example.limextestproject.ui.main.channelGroup.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.limextestproject.data.models.Channel
import com.example.limextestproject.databinding.ViewHolderChannelBinding

class ChannelsAdapter(
    private val createViewHolder: (ViewHolderChannelBinding) -> ChannelsViewHolder
) : RecyclerView.Adapter<ChannelsViewHolder>() {

    private val differ = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<Channel>() {
            override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
                return oldItem === newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
                return oldItem == newItem
            }
        }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder =
        createViewHolder(ViewHolderChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) {
        holder.bindTo(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun swapItems(newItems: List<Channel>) {
        differ.submitList(newItems)
    }
}
