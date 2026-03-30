package com.thehalotech.nasspectra.feature_dashboard.data.remote

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.NetworkDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.SystemInfoDto
import retrofit2.http.GET

interface SystemApi {
    @GET("system/info")
    suspend fun getSystemStats() : SystemInfoDto

    @GET("interface")
    suspend fun getNetworkStats() : List<NetworkDto>
}