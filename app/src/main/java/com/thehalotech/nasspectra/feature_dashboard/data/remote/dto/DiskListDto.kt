package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class DiskListDto(
    val used: List<DisksDto>,
    val unused: List<DisksDto>
)
