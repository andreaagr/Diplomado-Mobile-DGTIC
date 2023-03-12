package com.example.recetapp.di

import com.example.recetapp.networking.RemoteApi
import com.example.recetapp.repository.AuthRepository
import com.example.recetapp.repository.AuthRepositoryImpl
import com.example.recetapp.repository.RecipesRepository
import com.example.recetapp.repository.RecipesRepositoryImpl
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
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

    @Provides
    @Singleton
    fun provideAuthRepository(
        oneTapClient: SignInClient,
        signInRequest: BeginSignInRequest,
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(oneTapClient, signInRequest, firebaseAuth)
}