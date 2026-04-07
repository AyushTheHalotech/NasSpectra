package com.thehalotech.nasspectra.feature_dashboard.presentation.state

import com.thehalotech.nasspectra.feature_dashboard.domain.model.AppInfo

data class AppScreenState(
    val appList: SectionState<List<AppInfo>> = SectionState()
)
