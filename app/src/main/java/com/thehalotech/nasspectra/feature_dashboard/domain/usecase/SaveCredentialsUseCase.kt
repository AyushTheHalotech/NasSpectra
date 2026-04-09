package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import com.thehalotech.nasspectra.feature_dashboard.domain.repository.CredentialsRepository
import javax.inject.Inject

class SaveCredentialsUseCase @Inject constructor(
    private val repository: CredentialsRepository,
) {
    suspend operator fun invoke(ip: String, apiKey: String) {
        val baseUrl = "http://$ip/api/v2.0/"
        repository.saveCredentials(baseUrl, apiKey)
    }
}