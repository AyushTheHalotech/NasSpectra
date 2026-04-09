package com.thehalotech.nasspectra.feature_dashboard.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thehalotech.nasspectra.feature_dashboard.data.local.datastore.PrefKeys
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.CredentialsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CredentialRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : CredentialsRepository {
    override suspend fun saveCredentials(baseUrl: String, apiKey: String) {
        dataStore.edit {
            it[PrefKeys.BASE_URL] = baseUrl
            it[PrefKeys.API_KEY] = apiKey
        }
    }

    override fun getCredentials(): Flow<Pair<String, String>> {
        return dataStore.data.map {
            (it[PrefKeys.BASE_URL] ?: "") to (it[PrefKeys.API_KEY] ?: "")
        }
    }
}