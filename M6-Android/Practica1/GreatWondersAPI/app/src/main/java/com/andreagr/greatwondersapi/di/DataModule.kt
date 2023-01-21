package com.andreagr.greatwondersapi.di

import com.andreagr.greatwondersapi.repository.GreatWonderRepository
import com.andreagr.greatwondersapi.repository.GreatWonderRepositoryImpl
import com.andreagr.greatwondersapi.networking.RemoteApi
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
    fun provideRepository(remoteApi: RemoteApi)
    : GreatWonderRepository = GreatWonderRepositoryImpl(remoteApi)
}