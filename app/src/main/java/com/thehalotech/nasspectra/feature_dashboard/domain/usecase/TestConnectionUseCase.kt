package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import com.thehalotech.nasspectra.feature_dashboard.domain.core.SessionManager
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.ErrorMapper
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import javax.inject.Inject

class TestConnectionUseCase @Inject constructor(
    private val repo : SystemRepository,
    private val errorMapper: ErrorMapper,
    private val sessionManager: SessionManager
){
    suspend operator fun invoke(ip: String, apiKey: String): Result<Boolean> {
        return try {
            val baseUrl = "http://$ip/api/v2.0/"
            sessionManager.update(baseUrl, apiKey)
            repo.getNetworkStats()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(errorMapper.map(e))
        }
    }
}