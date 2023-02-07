package com.andreagr.greatwondersapi.di

import android.content.Context
import com.andreagr.greatwondersapi.ResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideResourceManager(@ApplicationContext applicationContext: Context)
        = ResourceManager(applicationContext)
}