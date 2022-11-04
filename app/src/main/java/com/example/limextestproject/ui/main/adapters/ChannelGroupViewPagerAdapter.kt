package com.example.limextestproject.ui.main.adapters

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.limextestproject.ui.main.channelGroup.ChannelGroupFragment
import kotlinx.parcelize.Parcelize

class ChannelGroupViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        @Parcelize
        enum class ChannelGroups(val title: String) : Parcelable {
            ALL("Все"),
            FAVORITES("Избранные")
        }
    }

    private val items = ChannelGroups.values()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return when (val channelGroup = items[position]) {
            ChannelGroups.ALL -> {
                ChannelGroupFragment.newInstance(channelGroup)
            }
            ChannelGroups.FAVORITES -> {
                ChannelGroupFragment.newInstance(channelGroup)
            }
        }
    }
}
