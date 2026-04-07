package com.thehalotech.nasspectra.feature_dashboard.presentation.state

import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.PoolState

data class DashboardState(
    val network: SectionState<NetworkState> = SectionState(),
    val pools: SectionState<List<PoolState>> = SectionState(),
    val tempSection: TempSectionState = TempSectionState(),
    val statSection: StatSectionState = StatSectionState()
)
