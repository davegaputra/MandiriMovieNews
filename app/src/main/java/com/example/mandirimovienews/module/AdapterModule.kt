package com.example.mandirimovienews.module

import com.example.mandirimovienews.adapter.GenreDetailAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun bindAdapterGenreDetail(): GenreDetailAdapter = GenreDetailAdapter(mutableListOf())
}