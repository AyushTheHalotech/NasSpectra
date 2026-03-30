package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.ErrorMapper
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import jakarta.inject.Inject

class GetSystemStatsUseCase @Inject constructor(
    private val repository: SystemRepository,
    private val errorMapper: ErrorMapper
) {
    suspend operator fun invoke(): Result<SystemStats> {
        return try {
            val data = repository.getSystemStats()
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(errorMapper.map(e))
        }
    }
}