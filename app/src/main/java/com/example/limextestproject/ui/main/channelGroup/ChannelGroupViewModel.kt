package com.example.limextestproject.ui.main.channelGroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.limextestproject.data.models.Channels
import com.example.limextestproject.data.remote.repositories.IChannelsRepository
import com.example.limextestproject.ui.main.adapters.ChannelGroupViewPagerAdapter.Companion.ChannelGroups
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@AssistedFactory
interface ChannelGroupViewModelAssistedFactory {
    fun create(channelGroup: ChannelGroups): ChannelGroupViewModel
}

class ChannelGroupViewModel @AssistedInject constructor(
    private val repository: IChannelsRepository,
    @Assisted private val channelGroup: ChannelGroups
) : ViewModel() {

    val channels: LiveData<List<Channels>> = liveData {
        emit(repository.getPlaylist().channels)
    }

    val currentChannelGroup = channelGroup
}
