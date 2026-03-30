package com.thehalotech.nasspectra.feature_dashboard.data.mapper

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.NetworkDto
import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState

fun NetworkDto.toDomain(): NetworkState {
    return NetworkState(
        id = id,
        name = name,
        linkState = state.link_state,
        iNetAddress = state.aliases.firstOrNull()?.address ?: "",
        linkSpeed = state.active_media_subtype
    )
}