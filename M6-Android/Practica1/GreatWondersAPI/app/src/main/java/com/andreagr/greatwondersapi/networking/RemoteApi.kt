package com.andreagr.greatwondersapi.networking

import com.andreagr.greatwondersapi.util.UIResponseState
import javax.inject.Inject

class RemoteApi @Inject constructor(
    private val greatWondersService: GreatWondersService
) {
    suspend fun getSevenWonders() = try {
        UIResponseState.Success(greatWondersService.getSevenWonders())
    } catch (error: Throwable) {
        UIResponseState.Error(error.message ?: "Something went wrong")
    }
}