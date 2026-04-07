package com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.GetPoolStateUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.ObserveDiskStatesUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.StorageState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toError
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class StorageViewModel @Inject constructor(
    val observeStorageUseCase: ObserveDiskStatesUseCase,
    val observePoolStatesUseCase: GetPoolStateUseCase
): ViewModel() {

    private val _state = MutableStateFlow(StorageState())
    val state: StateFlow<StorageState> = _state

    init {
        observeStorage()
        observePools()
    }

    fun observePools() {

        viewModelScope.launch {
            _state.update {
                it.copy(
                    pools = it.pools.toLoading()
                )
            }
            observePoolStatesUseCase.flow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        _state.update {
                            it.copy(
                                pools = it.pools.copy(
                                    data = data,
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
                                pools = it.pools.toError(result.error)
                            )
                        }
                    }
                }

            }
        }

    }

    fun observeStorage() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    disks = it.disks.toLoading()
                )
            }
            observeStorageUseCase.flow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        _state.update {
                            it.copy(
                                disks = it.disks.copy(
                                    data = data,
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
                                disks = it.disks.toError(result.error)
                                )
                        }
                    }
                }

            }
        }
    }
}