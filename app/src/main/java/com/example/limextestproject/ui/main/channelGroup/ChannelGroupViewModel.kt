package com.example.limextestproject.ui.main.channelGroup

import androidx.lifecycle.*
import com.example.limextestproject.data.local.repositories.IFavoriteChannelsRepository
import com.example.limextestproject.data.models.Channels
import com.example.limextestproject.data.remote.repositories.IChannelsRepository
import com.example.limextestproject.ui.main.adapters.ChannelGroupViewPagerAdapter.Companion.ChannelGroups
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AssistedFactory
interface ChannelGroupViewModelAssistedFactory {
    fun create(channelGroup: ChannelGroups): ChannelGroupViewModel
}

class ChannelGroupViewModel @AssistedInject constructor(
    private val repository: IChannelsRepository,
    private val favoriteChannelsRepository: IFavoriteChannelsRepository,
    @Assisted private val channelGroup: ChannelGroups
) : ViewModel() {

    private val _channels: MutableLiveData<List<Channels>> = MutableLiveData()
    val channels: LiveData<List<Channels>> = _channels.map {
        filterByChannelGroup(it)
    }

    init {
        viewModelScope.launch {
            favoriteChannelsRepository.getFavoriteChannelIdsFlow().collect {
                _channels.value = updateChannelsFavoriteState(
                    _channels.value ?: repository.getChannels(),
                    it
                )
            }
        }
    }

    fun addChannelToFavorite(updatedChannel: Channels) {
        updateFavoriteGroup(updatedChannel)
    }

    fun filterChannels(query: String) {
        viewModelScope.launch {
            _channels.value = updateChannelsFavoriteState(
                repository.getChannels().filter { it.name.contains(query) },
                favoriteChannelsRepository.getFavoriteChannelIds()
            )
        }
    }

    private fun updateFavoriteGroup(channel: Channels) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (channel.isFavorite) {
                    favoriteChannelsRepository.removeChannelFromFavoriteGroup(channel.id)
                } else {
                    favoriteChannelsRepository.addChannelToFavoriteGroup(channel.id)
                }
            }
        }
    }

    private fun filterByChannelGroup(allChannels: List<Channels>): List<Channels> {
        return allChannels.filter {
            when (channelGroup) {
                ChannelGroups.FAVORITES -> it.isFavorite
                else -> true
            }
        }
    }

    private fun updateChannelsFavoriteState(channels: List<Channels>, favoriteChannelIds: List<Long>): List<Channels> {
        return channels.toMutableList().apply {
            forEachIndexed { index, channel ->
                if (favoriteChannelIds.contains(channel.id) && channel.isFavorite.not()) {
                    this@apply[index] = channel.copy(isFavorite = true)
                } else if (favoriteChannelIds.contains(channel.id).not() && channel.isFavorite
                ) {
                    this@apply[index] = channel.copy(isFavorite = false)
                }
            }
        }
    }
}
