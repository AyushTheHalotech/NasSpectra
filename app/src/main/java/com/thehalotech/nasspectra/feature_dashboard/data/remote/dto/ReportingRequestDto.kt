package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class ReportingRequestDto(
    val graphs: List<RequestGraph>
)

data class RequestGraph(
    val name: String,
)
