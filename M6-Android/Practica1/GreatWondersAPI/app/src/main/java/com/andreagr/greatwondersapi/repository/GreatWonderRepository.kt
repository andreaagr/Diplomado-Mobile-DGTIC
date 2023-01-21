package com.andreagr.greatwondersapi.repository

import com.andreagr.greatwondersapi.util.UIResponseState

interface GreatWonderRepository {
    suspend fun getRemoteGreatWonders(): UIResponseState
}