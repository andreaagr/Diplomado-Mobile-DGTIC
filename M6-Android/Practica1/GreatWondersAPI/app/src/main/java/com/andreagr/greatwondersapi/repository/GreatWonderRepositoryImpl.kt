package com.andreagr.greatwondersapi.repository

import com.andreagr.greatwondersapi.networking.RemoteApi
import com.andreagr.greatwondersapi.util.UIResponseState
import javax.inject.Inject

class GreatWonderRepositoryImpl @Inject constructor(
    private val remoteApi: RemoteApi
): GreatWonderRepository {
    override suspend fun getRemoteGreatWonders(): UIResponseState {
        return remoteApi.getSevenWonders()
    }
}