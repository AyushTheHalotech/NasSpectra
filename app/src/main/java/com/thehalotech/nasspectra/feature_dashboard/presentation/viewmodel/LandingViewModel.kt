package com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.GetCredentialsUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.SaveCredentialsUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.TestConnectionUseCase
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.LandingState
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val saveCredentials: SaveCredentialsUseCase,
    private val getCredentials: GetCredentialsUseCase,
    private val testConnection: TestConnectionUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LandingState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCredentials().collect {
                (ip, key) ->
                _state.update {
                    it.copy(ip = ip, apiKey = key)
                }
            }
        }
    }

    fun onIpChange(value: String) {
        _state.update { it.copy(ip = value, error = null) }
    }

    fun onApiKeyChange(value: String) {
        _state.update { it.copy(apiKey = value) }
    }

    fun onTestConnection() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            when (val result = testConnection(_state.value.ip, _state.value.apiKey)) {
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, isConnected = true) }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(isLoading = false, isConnected = false, error = "Connection failed")
                    }
                }
            }
        }
    }

    fun onNext(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val state = _state.value

            saveCredentials(state.ip, state.apiKey)

            if (state.isConnected) {
                onSuccess()
            } else {
                _state.update { it.copy(error = "Test connection first") }
            }
        }
    }
}