package com.thehalotech.nasspectra.feature_dashboard.data.repository

import android.util.Log
import com.thehalotech.nasspectra.feature_dashboard.data.mapper.toDomain
import com.thehalotech.nasspectra.feature_dashboard.data.remote.SystemApi
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.DiskTempRequest
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.ReportingRequestDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.RequestGraph
import com.thehalotech.nasspectra.feature_dashboard.domain.model.AppInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.PoolState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.ReportingInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.mergeTemperatures
import jakarta.inject.Inject
import kotlin.collections.listOf

class SystemRepositoryImpl @Inject constructor(
    private val api: SystemApi
): SystemRepository {

    override suspend fun getNetworkStats(): NetworkState {
        return api.getNetworkStats().first().toDomain()
    }

    override suspend fun getPoolDetails(): List<PoolState> {
        return api.getPoolDetails().map { it.toDomain() }
    }

    override suspend fun getDisksWithTemperature(): List<DiskInfo> {
        val detailsResponse = api.getDiskData()

        val diskDtos = detailsResponse.used
        val disks = diskDtos.toDomain()
        val diskNames = diskDtos.map { it.name }
        if (diskNames.isEmpty()) return emptyList()

        val tempMap = api.getTemperatures(
            DiskTempRequest(name = diskNames)
        )

        return disks
            .mergeTemperatures(tempMap)
            .sortedByDescending { it.temperature ?: Double.MIN_VALUE }
    }

    override suspend fun getReportingData(): ReportingInfo {
        val totalMemResponse = api.getSystemStats()
        val physMem = totalMemResponse.physmem
        val graphItemCpu = RequestGraph(name = "cpu")
        val graphItemCputemp = RequestGraph(name = "cputemp")
        val graphItemMemory = RequestGraph(name = "memory")
        val body = ReportingRequestDto(graphs = listOf(
            graphItemCpu,
            graphItemCputemp,
            graphItemMemory)
        )

        val response = api.getReportingData(body)
        return response.toDomain(physMem, totalMemResponse)

    }

    override suspend fun getAppDetails(): List<AppInfo> {
        val response = api.getAppDetails()
        return response.map { it.toDomain() }
    }


}