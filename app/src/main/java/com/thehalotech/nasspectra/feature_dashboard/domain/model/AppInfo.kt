package com.thehalotech.nasspectra.feature_dashboard.domain.model


data class AppInfo(
    val id: String,
    val name: String,
    val state: String,
    val upgradeAvailable: Boolean,
    val latestVersion: String,
    val version: String,
    val dateAdded: String,
    val description: String,
    val icon: String,
    val maintainers: List<AppMaintainersInfo>,
    val train: String,
    val portals: Map<String, String>
)

data class AppMaintainersInfo(
    val email: String,
    val name: String
)
