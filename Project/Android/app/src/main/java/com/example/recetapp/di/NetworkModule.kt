package com.example.recetapp.di

import com.example.recetapp.networking.FoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("x-api-key", "b46e9574028f468db982ee1e50a72c7a")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.spoonacular.com/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGreatWonderService(retrofit: Retrofit): FoodService {
        return retrofit.create(FoodService::class.java)
    }
}