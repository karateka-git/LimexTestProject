package com.example.limextestproject.data.remote.repositories

import com.example.limextestproject.data.remote.services.ChannelsService
import javax.inject.Inject

class ChannelsRepository @Inject constructor(
    private val service: ChannelsService
) : IChannelsRepository {

    override suspend fun getPlaylist() = service.getPlaylist()
}
