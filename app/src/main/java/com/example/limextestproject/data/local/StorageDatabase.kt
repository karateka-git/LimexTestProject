package com.example.limextestproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.limextestproject.data.local.daos.FavoriteChannelsDao
import com.example.limextestproject.data.local.entities.ChannelEntity

@Database(
    entities = [
        ChannelEntity::class
    ],
    version = 1
)
abstract class StorageDatabase : RoomDatabase() {
    abstract fun favoriteChannelsDao(): FavoriteChannelsDao
}
