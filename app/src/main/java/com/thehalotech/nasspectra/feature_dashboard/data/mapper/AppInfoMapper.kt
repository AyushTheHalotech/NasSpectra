package com.thehalotech.nasspectra.feature_dashboard.data.mapper

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.AppDto
import com.thehalotech.nasspectra.feature_dashboard.domain.model.AppInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.AppMaintainersInfo

fun AppDto.toDomain(): AppInfo {
    return AppInfo(
        id = id,
        name = name,
        state = state,
        upgradeAvailable = upgrade_available,
        latestVersion = latest_version,
        version = version,
        dateAdded = metadata.date_added,
        description = metadata.description,
        icon = metadata.icon,
        maintainers = metadata.maintainers.map {
            AppMaintainersInfo(
                email = it.email,
                name = it.name
            )
        },
        train = metadata.train,
        portals = portals
    )
}