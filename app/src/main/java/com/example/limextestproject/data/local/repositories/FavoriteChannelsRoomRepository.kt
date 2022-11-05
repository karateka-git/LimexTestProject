package com.example.limextestproject.data.local.repositories

import com.example.limextestproject.data.local.daos.FavoriteChannelsDao
import com.example.limextestproject.data.local.entities.ChannelEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteChannelsRoomRepository @Inject constructor(
    private val favoriteChannelsDao: FavoriteChannelsDao
) : IFavoriteChannelsRepository {

    override suspend fun addChannelToFavoriteGroup(channelId: Long) = withContext(Dispatchers.IO) {
        favoriteChannelsDao.insert(ChannelEntity(channelId))
    }

    override fun getFavoriteChannelIdsFlow(): Flow<List<Long>> {
        return favoriteChannelsDao.getChannelIdsListFlow().map {
            it.map { channelEntity -> channelEntity.id }
        }
    }

    override suspend fun getFavoriteChannelIds(): List<Long> = withContext(Dispatchers.IO) {
        favoriteChannelsDao.getChannelIdsList().map { it.id }
    }

    override suspend fun removeChannelFromFavoriteGroup(channelId: Long) = withContext(Dispatchers.IO) {
        favoriteChannelsDao.remove(channelId)
    }
}
