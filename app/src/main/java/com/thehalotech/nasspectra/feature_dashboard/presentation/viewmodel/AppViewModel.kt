package com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thehalotech.nasspectra.feature_dashboard.domain.usecase.FetchAppDetailsUseCase
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.AppScreenState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toError
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class AppViewModel @Inject constructor(
    val observeApps: FetchAppDetailsUseCase
): ViewModel()
{

    private val _state = MutableStateFlow(AppScreenState())
    val state: StateFlow<AppScreenState> = _state

    init {
        startAppPolling()
    }

    private var retrieveAppJob: Job? = null

    fun loadApps() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    appList = it.appList.toLoading())
            }

            when (val result = observeApps()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            appList = it.appList.copy(
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
                            appList = it.appList.toError(
                                result.error
                            )
                        )
                    }
                }
            }
        }
    }

    fun startAppPolling() {
        if(retrieveAppJob?.isActive == true) return

        retrieveAppJob = viewModelScope.launch {
            while(isActive) {
                loadApps()
                delay(15.minutes)
            }
        }
    }
}