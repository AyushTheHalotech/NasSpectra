package com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.GetNetworkStateUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.GetSystemStatsUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.util.DomainError
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.DashboardState
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.SectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toUiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getSystemStats: GetSystemStatsUseCase,
    private val getNetworkState: GetNetworkStateUseCase
): ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    private var systemJob: Job? = null
    private var networkJob: Job? = null

    fun loadSystemStats() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    system = it.system.copy(
                        isRefreshing = it.system.data != null,
                        isLoading = it.system.data == null))
            }

            when (val result = getSystemStats()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            system = it.system.copy(
                                data = result.data,
                                isLoading = false,
                                isRefreshing = false,
                                error = null
                            )
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            system = it.system.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = result.error
                            )
                        )
                    }
                }
            }
        }
    }

    fun loadNetworkStats() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    network = it.network.copy(
                        isRefreshing = it.network.data != null,
                        isLoading = it.network.data == null))
            }
            when (val result = getNetworkState()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            network = it.network.copy(
                                data = result.data,
                                isLoading = false,
                                isRefreshing = false,
                                error = null
                            )
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            network = it.network.copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = result.error
                            )
                        )
                    }
                }
            }
        }
    }

    fun startSystemPolling() {
        if(systemJob?.isActive == true) return

        systemJob = viewModelScope.launch {
            while(isActive) {
                loadSystemStats()
                delay(3000)
            }
        }
    }

    fun startNetworkPolling() {
        if(networkJob?.isActive == true) return

        networkJob = viewModelScope.launch {
            while(isActive) {
                loadNetworkStats()
                delay(1000000)
            }
        }
    }

    fun startPolling() {
        startSystemPolling()
        startNetworkPolling()
    }

    fun stopPolling() {
        systemJob?.cancel()
        networkJob?.cancel()
    }


    fun <T> Result<T>.getOrNull(): T? =
        (this as? Result.Success)?.data


    private fun mergeErrors(
        system: Result<*>,
        network: Result<*>
    ): String? {
        val errors = listOfNotNull(
            (system as? Result.Error)?.error?.toUiMessage(),
            (network as? Result.Error)?.error?.toUiMessage()
        )

        return if (errors.isNotEmpty()) errors.joinToString("\n") else null
    }
}