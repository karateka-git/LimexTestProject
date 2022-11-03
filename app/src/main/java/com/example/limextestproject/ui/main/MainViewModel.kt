package com.example.limextestproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.limextestproject.data.models.Playlist
import com.example.limextestproject.data.remote.repositories.IChannelsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IChannelsRepository
) : ViewModel() {

    val channels: LiveData<Playlist> = liveData {
        emit(repository.getPlaylist())
    }
}
