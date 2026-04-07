package com.thehalotech.nasspectra.feature_dashboard.presentation.state

import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats

data class MainScreenState(
    val systemState: SectionState<SystemStats> = SectionState()
)
