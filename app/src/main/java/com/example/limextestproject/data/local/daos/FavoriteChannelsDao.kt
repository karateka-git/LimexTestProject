package com.example.limextestproject.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.limextestproject.data.local.entities.ChannelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteChannelsDao {

    @Query("SELECT * FROM favoriteChannels")
    fun getChannelIdsListFlow(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM favoriteChannels")
    fun getChannelIdsList(): List<ChannelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channelId: ChannelEntity)

    @Query("DELETE FROM favoriteChannels WHERE id == :channelId")
    suspend fun remove(channelId: Long)
}
