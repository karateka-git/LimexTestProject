package com.example.limextestproject.data.models

import com.google.gson.annotations.SerializedName

data class Playlist(
    val channels: List<Channels>
)

data class Channels(
    @SerializedName("name_ru")
    val name: String,
    val image: String,
    val url: String,
    @SerializedName("current")
    val currentProgram: Program
)

data class Program(
    val title: String
)
