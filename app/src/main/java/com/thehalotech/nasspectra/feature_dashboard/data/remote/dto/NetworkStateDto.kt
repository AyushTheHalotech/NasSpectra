package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class NetworkStateDto(
    val link_state: String,
    val active_media_subtype: String,
    val active_media_type: String,
    val aliases: List<AliasesDto>
)
