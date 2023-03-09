package com.example.recetapp.di

import com.example.recetapp.networking.RemoteApi
import com.example.recetapp.repository.RecipesRepository
import com.example.recetapp.repository.RecipesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideRepository(remoteApi: RemoteApi): RecipesRepository = RecipesRepositoryImpl(remoteApi)
}