package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import com.thehalotech.nasspectra.feature_dashboard.domain.repository.CredentialsRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.ErrorMapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCredentialsUseCase @Inject constructor(
    private val repository: CredentialsRepository,
    private val errorMapper: ErrorMapper
) {
    suspend operator fun invoke(): Flow<Pair<String, String>> {
        return repository.getCredentials()
    }
}