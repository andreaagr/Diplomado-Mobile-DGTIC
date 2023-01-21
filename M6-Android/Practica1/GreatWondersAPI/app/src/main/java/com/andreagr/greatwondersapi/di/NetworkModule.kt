package com.andreagr.greatwondersapi.di

import com.andreagr.greatwondersapi.networking.GreatWondersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://private-0e73ac-worldwonders.apiary-mock.com/")
            .build()
    }

    @Provides
    @Singleton
    fun provideGreatWonderService(retrofit: Retrofit): GreatWondersService {
        return retrofit.create(GreatWondersService::class.java)
    }
}