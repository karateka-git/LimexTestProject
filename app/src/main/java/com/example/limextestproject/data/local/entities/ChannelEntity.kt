package com.example.limextestproject.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteChannels")
data class ChannelEntity(
    @PrimaryKey val id: Long
)
