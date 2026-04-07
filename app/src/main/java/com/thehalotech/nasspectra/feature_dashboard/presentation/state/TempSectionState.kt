package com.thehalotech.nasspectra.feature_dashboard.presentation.state

import com.thehalotech.nasspectra.feature_dashboard.domain.model.CpuTempsState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo

data class TempSectionState(
    val cpuTempState: SectionState<CpuTempsState> = SectionState(),
    val diskTempState: SectionState<List<DiskInfo>> = SectionState()
)