package com.example.limextestproject.data.models

import com.google.gson.annotations.SerializedName

data class Playlist(
    val channels: List<Channel>
)

data class Channel(
    val id: Long,
    @SerializedName("name_ru")
    val name: String,
    val image: String,
    val url: String,
    @SerializedName("current")
    val currentProgram: Program,
    var isFavorite: Boolean = false
)

data class Program(
    val title: String
)
