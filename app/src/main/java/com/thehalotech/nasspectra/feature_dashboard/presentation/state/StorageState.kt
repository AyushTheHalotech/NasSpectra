package com.thehalotech.nasspectra.feature_dashboard.presentation.state

import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.PoolState

data class StorageState(
    val pools: SectionState<List<PoolState>> = SectionState(),
    val disks: SectionState<List<DiskInfo>> = SectionState(),
)
