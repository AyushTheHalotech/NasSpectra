package com.thehalotech.nasspectra.feature_dashboard.data.remote.dto

data class AppDto(
    val id: String,
    val name: String,
    val state: String,
    val upgrade_available: Boolean,
    val latest_version: String,
    val version: String,
    val metadata: AppMetaDataDto,
    val portals: Map<String, String>
)

data class AppMetaDataDto(
    val date_added: String,
    val description: String,
    val icon: String,
    val maintainers: List<AppMaintainerDto>,
    val train: String
)

data class AppMaintainerDto(
    val email: String,
    val name: String
)
