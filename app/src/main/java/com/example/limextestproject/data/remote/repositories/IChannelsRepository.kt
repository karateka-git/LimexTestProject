package com.example.limextestproject.data.remote.repositories

import com.example.limextestproject.data.models.Playlist

interface IChannelsRepository {
    suspend fun getPlaylist(): Playlist
}