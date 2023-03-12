package com.example.recetapp.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recetapp.data.RecipeDao
import com.example.recetapp.data.RecipeDatabase
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideRecipeDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RecipeDatabase::class.java,
        "recipe-db"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideYourDao(db: RecipeDatabase) = db.getRecipeDao()
    @Provides
    @Singleton
    fun provideRepository(
        remoteApi: RemoteApi,
        recipeDao: RecipeDao
    ): RecipesRepository = RecipesRepositoryImpl(remoteApi, recipeDao)

    @Provides
    @Singleton
    fun provideAuthRepository(
        oneTapClient: SignInClient,
        signInRequest: BeginSignInRequest,
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(oneTapClient, signInRequest, firebaseAuth)

    /*@Provides
    @Singleton
    fun provideOfflineDataSource(
        @ApplicationContext applicationContext: Context
    ): RoomDatabase = Room.databaseBuilder(
        applicationContext,
        RecipeDatabase::class.java, "recipe-database"
    ).build()*/


}