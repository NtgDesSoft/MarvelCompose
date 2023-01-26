package com.app.marvel.di

import com.app.marvel.Repository
import com.app.marvel.RepositoryImpl
import com.app.marvel.api.CharacterEndPoints
import com.app.marvel.api.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesAPI() = RetrofitInstance.API

    @Provides
    @Singleton
    fun providesRepository(api: CharacterEndPoints) :Repository= RepositoryImpl(api)
}