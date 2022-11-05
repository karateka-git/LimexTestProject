package com.example.limextestproject.data.local.repositories

import kotlinx.coroutines.flow.Flow

interface IFavoriteChannelsRepository {

    suspend fun addChannelToFavoriteGroup(channelId: Long)

    fun getFavoriteChannelIdsFlow(): Flow<List<Long>>

    suspend fun getFavoriteChannelIds(): List<Long>

    suspend fun removeChannelFromFavoriteGroup(channelId: Long)
}
