package com.example.limextestproject.data.remote.repositories

import com.example.limextestproject.data.models.Channels

interface IChannelsRepository {

    suspend fun getChannels(): List<Channels>
}
