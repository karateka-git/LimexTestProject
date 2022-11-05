package com.example.limextestproject.data.remote.repositories

import com.example.limextestproject.data.models.Channel

interface IChannelsRepository {

    suspend fun getChannels(): List<Channel>
}
