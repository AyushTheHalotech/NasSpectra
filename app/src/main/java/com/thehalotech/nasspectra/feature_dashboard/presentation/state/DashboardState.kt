package com.thehalotech.nasspectra.feature_dashboard.presentation.state

import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats

data class DashboardState(
    val system: SectionState<SystemStats> = SectionState(),
    val network: SectionState<NetworkState> = SectionState()
)
