package com.thehalotech.nasspectra.feature_dashboard.domain.repository

import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats

interface SystemRepository {

    suspend fun getSystemStats(): SystemStats

    suspend fun getNetworkStats(): NetworkState
}