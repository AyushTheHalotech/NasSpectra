package com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.nasspectra.feature_dashboard.domain.core.SessionManager
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.CredentialsRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: CredentialsRepository,
    private val sessionManager: SessionManager,
    private val systemRepository: SystemRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<String?>(null)
    val destination = _destination.asStateFlow()
    val startTime = System.currentTimeMillis()

    init {
        checkAndNavigate()
    }

    private fun checkAndNavigate() {
        viewModelScope.launch {
            val targetRoute = repository.getCredentials().first().let { (baseUrl, apiKey) ->

                if (baseUrl.isBlank() || apiKey.isBlank()) {
                    Screen.Landing.route
                }

                try {
                    sessionManager.update(baseUrl, apiKey)

                    // Try a lightweight API call
                    systemRepository.getNetworkStats()

                    Screen.Main.route
                } catch (e: Exception) {
                    Screen.Landing.route
                }
            }
            val elapsed = System.currentTimeMillis() - startTime
            if (elapsed < 800) delay(800 - elapsed)
            _destination.value = targetRoute
        }
    }
}