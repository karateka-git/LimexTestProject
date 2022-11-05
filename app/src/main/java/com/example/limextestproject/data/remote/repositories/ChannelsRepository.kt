package com.example.limextestproject.data.remote.repositories

import com.example.limextestproject.data.models.Channels
import com.example.limextestproject.data.remote.services.ChannelsService
import javax.inject.Inject

class ChannelsRepository @Inject constructor(
    private val service: ChannelsService
) : IChannelsRepository {

    private var cashedChannels: List<Channels> = listOf()

    override suspend fun getChannels(): List<Channels> =
        cashedChannels.ifEmpty {
            service.getPlaylist().channels.also {
                cashedChannels = it
            }
        }
}
