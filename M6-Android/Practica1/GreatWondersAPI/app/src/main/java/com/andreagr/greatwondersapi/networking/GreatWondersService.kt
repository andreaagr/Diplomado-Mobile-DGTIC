package com.andreagr.greatwondersapi.networking

import com.andreagr.greatwondersapi.model.GreatWonder
import retrofit2.http.GET

interface GreatWondersService {
    @GET("/wonders")
    suspend fun getSevenWonders(): List<GreatWonder>
}