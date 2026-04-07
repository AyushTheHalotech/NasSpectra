package com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.FetchReportingDataUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.GetNetworkStateUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.GetPoolStateUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.ObserveDiskStatesUseCase
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.DashboardState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toError
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNetworkState: GetNetworkStateUseCase,
    private val getPoolState: GetPoolStateUseCase,
    private val reportingUseCase: FetchReportingDataUseCase,
    private val observeDiskStatesUseCase: ObserveDiskStatesUseCase
): ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    init {
        loadReportingData()
        observeDisks()
        observeNetwork()
        observePoolState()
    }

    fun observePoolState() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    pools = it.pools.copy(
                        isRefreshing = it.pools.data != null,
                        isLoading = it.pools.data == null))
            }
            getPoolState.flow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                pools = it.pools.copy(
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
                                pools = it.pools.copy(
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
    }

    fun observeNetwork() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    network = it.network.copy(
                        isRefreshing = it.network.data != null,
                        isLoading = it.network.data == null)
                )
            }
            getNetworkState.flow.collect { result ->
                when (result) {
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
    }

    fun observeDisks() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    tempSection = it.tempSection.copy(
                        diskTempState = it.tempSection.diskTempState.toLoading()
                    )
                )
            }
            observeDiskStatesUseCase.flow.collect { result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.data
                        _state.update {
                            it.copy(
                                tempSection = it.tempSection.copy(
                                    diskTempState = it.tempSection.diskTempState.copy(
                                        data = data,
                                        isLoading = false,
                                        isRefreshing = false,
                                        error = null
                                    )
                                )
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                tempSection = it.tempSection.copy(
                                    diskTempState = it.tempSection.diskTempState.toError(result.error)
                                )
                            )
                        }
                    }
                }

            }
        }
    }

    fun loadReportingData() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    statSection = it.statSection.copy(
                        cpuUsage = it.statSection.cpuUsage.toLoading(),
                        memUsage = it.statSection.memUsage.toLoading()
                    ),
                    tempSection = it.tempSection.copy(
                        cpuTempState = it.tempSection.cpuTempState.toLoading()
                    )
                )
            }
            reportingUseCase.flow.collect { result ->
                when(result) {
                    is Result.Success -> {
                        val data = result.data
                        _state.update {
                            it.copy(
                                statSection = it.statSection.copy(
                                    cpuUsage = it.statSection.cpuUsage.copy(
                                        data = data.cpuUsage,
                                        isLoading = false,
                                        isRefreshing = false,
                                        error = null
                                    ),
                                    memUsage = it.statSection.memUsage.copy(
                                        data = data.memUsage,
                                        isLoading = false,
                                        isRefreshing = false,
                                        error = null
                                    )
                                ),
                                tempSection = it.tempSection.copy(
                                    cpuTempState = it.tempSection.cpuTempState.copy(
                                        data = data.cpuTemp,
                                        isLoading = false,
                                        isRefreshing = false,
                                        error = null
                                    )
                                )
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                statSection = it.statSection.copy(
                                    cpuUsage = it.statSection.cpuUsage.toError(result.error),
                                    memUsage = it.statSection.memUsage.toError(result.error)
                                ),
                                tempSection = it.tempSection.copy(
                                    cpuTempState = it.tempSection.cpuTempState.toError(result.error)
                                )
                            )
                        }
                    }
                }
            }
        }

    }

}