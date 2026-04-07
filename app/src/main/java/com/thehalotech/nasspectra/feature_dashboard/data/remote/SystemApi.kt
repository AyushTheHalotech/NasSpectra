package com.thehalotech.nasspectra.feature_dashboard.data.remote

import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.AppDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.DiskListDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.DiskTempRequest
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.DiskTempResponse
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.NetworkDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.PoolDataDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.ReportingRequestDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.ReportingResponseDto
import com.thehalotech.nasspectra.feature_dashboard.data.remote.dto.SystemInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SystemApi {

    @GET("interface")
    suspend fun getNetworkStats() : List<NetworkDto>

    @GET("pool")
    suspend fun  getPoolDetails(): List<PoolDataDto>

    @POST("disk/details")
    suspend fun getDiskData(
    ): DiskListDto

    @POST("disk/temperatures")
    suspend fun getTemperatures(
        @Body body: DiskTempRequest
    ): DiskTempResponse


    @POST("reporting/get_data")
    suspend fun getReportingData(
        @Body body: ReportingRequestDto
    ): List<ReportingResponseDto>

    @GET("app")
    suspend fun getAppDetails(): List<AppDto>

    @GET("system/info")
    suspend fun getSystemStats() : SystemInfoDto

}