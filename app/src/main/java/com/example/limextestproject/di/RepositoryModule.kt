package com.example.limextestproject.di

import com.example.limextestproject.data.local.repositories.FavoriteChannelsRoomRepository
import com.example.limextestproject.data.local.repositories.IFavoriteChannelsRepository
import com.example.limextestproject.data.remote.repositories.ChannelsRepository
import com.example.limextestproject.data.remote.repositories.IChannelsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindChannelsRepository(implementation: ChannelsRepository): IChannelsRepository

    @Singleton
    @Binds
    abstract fun bindFavoriteChannelsRepository(implementation: FavoriteChannelsRoomRepository): IFavoriteChannelsRepository
}
