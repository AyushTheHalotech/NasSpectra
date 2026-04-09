package com.thehalotech.nasspectra.feature_dashboard.domain.repository

import kotlinx.coroutines.flow.Flow

interface CredentialsRepository {

    suspend fun saveCredentials(baseUrl: String, apiKey: String)
    fun getCredentials(): Flow<Pair<String, String>>
}