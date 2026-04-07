package com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.FetchReportingDataUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.MainScreenState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toError
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    val observeSystem: FetchReportingDataUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    init {
        loadSystemStats()
    }

    fun loadSystemStats() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    systemState = it.systemState.toLoading()
                )
            }
            observeSystem.flow.collect { result ->
                when(result) {
                    is Result.Success -> {
                        val data = result.data
                        _state.update {
                            it.copy(
                                systemState = it.systemState.copy(
                                    data = data.systemInfo,
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
                                systemState = it.systemState.toError(result.error)
                            )
                        }
                    }
                }
            }
        }
    }
}