package com.andreagr.semana3.di

import android.content.Context
import com.andreagr.semana3.SqlHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun bindSqlHelper(@ApplicationContext context: Context) = SqlHelper(context)
}