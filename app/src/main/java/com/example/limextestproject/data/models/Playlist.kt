package com.example.limextestproject.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Playlist(
    val channels: List<Channel>
)

@Parcelize
data class Channel(
    val id: Long,
    @SerializedName("name_ru")
    val name: String,
    val image: String,
    val url: String,
    @SerializedName("current")
    val currentProgram: Program,
    var isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class Program(
    val title: String,
    val timestart: Long,
    val timestop: Long
) : Parcelable
