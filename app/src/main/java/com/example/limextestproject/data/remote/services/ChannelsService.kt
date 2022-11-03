package com.example.limextestproject.data.remote.services

import com.example.limextestproject.data.models.Playlist
import retrofit2.http.GET

interface ChannelsService {
    @GET("channels.json")
    suspend fun getPlaylist(): Playlist
}
