package com.thehalotech.nasspectra.feature_dashboard.presentation.state

import com.thehalotech.nasspectra.feature_dashboard.domain.model.CpuUsageInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.MemoryStatInfo

data class StatSectionState (
    val cpuUsage: SectionState<CpuUsageInfo> = SectionState(),
    val memUsage: SectionState<MemoryStatInfo> = SectionState()
)