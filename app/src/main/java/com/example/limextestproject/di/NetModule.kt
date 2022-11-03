package com.example.limextestproject.di

import com.example.limextestproject.data.remote.services.ChannelsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://limehd.online/playlist/")
        .build()

    @Provides
    @Singleton
    fun provideChannelsService(retrofit: Retrofit): ChannelsService = retrofit.create(ChannelsService::class.java)
}
