package com.thehalotech.nasspectra.feature_dashboard.domain.core


import com.thehalotech.nasspectra.application.ApplicationScope
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.CredentialsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val repository: CredentialsRepository,
    @param:ApplicationScope private val scope: CoroutineScope
) {

    private val _credentials = MutableStateFlow("" to "")
    val credentials: StateFlow<Pair<String, String>> = _credentials.asStateFlow()

    init{
        scope.launch {
            repository.getCredentials().collect {
                _credentials.value = it
            }
        }
    }

    fun update(baseUrl: String, apiKey: String) {
        _credentials.value = baseUrl to apiKey
    }
}