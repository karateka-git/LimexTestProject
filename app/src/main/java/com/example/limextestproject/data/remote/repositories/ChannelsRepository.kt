package com.example.limextestproject.data.remote.repositories

import com.example.limextestproject.data.models.Channel
import com.example.limextestproject.data.remote.services.ChannelsService
import javax.inject.Inject

class ChannelsRepository @Inject constructor(
    private val service: ChannelsService
) : IChannelsRepository {

    private var cashedChannels: List<Channel> = listOf()

    override suspend fun getChannels(): List<Channel> =
        cashedChannels.ifEmpty {
            service.getPlaylist().channels.also {
                cashedChannels = it
            }
        }
}
