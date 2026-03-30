package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import com.thehalotech.nasspectra.feature_dashboard.domain.util.ErrorMapper
import jakarta.inject.Inject

class GetNetworkStateUseCase @Inject constructor(
    private val repository: SystemRepository,
    private val errorMapper: ErrorMapper
) {
    suspend operator fun invoke(): Result<NetworkState> {
        return try {
            val data = repository.getNetworkStats()
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(errorMapper.map(e))
        }
    }
}