package com.thehalotech.nasspectra.feature_dashboard.data.repository

import com.thehalotech.nasspectra.feature_dashboard.data.mapper.toDomain
import com.thehalotech.nasspectra.feature_dashboard.data.remote.SystemApi
import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import jakarta.inject.Inject

class SystemRepositoryImpl @Inject constructor(
    private val api: SystemApi
): SystemRepository {
    override suspend fun getSystemStats(): SystemStats {
        return api.getSystemStats().toDomain()
    }

    override suspend fun getNetworkStats(): NetworkState {
        return api.getNetworkStats().first().toDomain()
    }
}