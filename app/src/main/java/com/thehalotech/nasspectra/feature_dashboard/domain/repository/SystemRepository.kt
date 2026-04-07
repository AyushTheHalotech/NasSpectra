package com.thehalotech.nasspectra.feature_dashboard.domain.repository

import com.thehalotech.nasspectra.feature_dashboard.domain.model.AppInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.PoolState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.ReportingInfo

interface SystemRepository {

    suspend fun getNetworkStats(): NetworkState

    suspend fun getPoolDetails(): List<PoolState>

    suspend fun getDisksWithTemperature(): List<DiskInfo>

    suspend fun getReportingData(): ReportingInfo

    suspend fun getAppDetails(): List<AppInfo>

}