package com.example.limextestproject.di

import android.content.Context
import androidx.room.Room
import com.example.limextestproject.data.local.StorageDatabase
import com.example.limextestproject.data.local.daos.FavoriteChannelsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideStorageDatabase(
        @ApplicationContext context: Context
    ): StorageDatabase =
        Room.databaseBuilder(context, StorageDatabase::class.java, "storage_database.db").build()

    @Singleton
    @Provides
    fun provideTrainingPlanDao(
        database: StorageDatabase
    ): FavoriteChannelsDao =
        database.favoriteChannelsDao()
}
