package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import com.thehalotech.nasspectra.application.ApplicationScope
import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import com.thehalotech.nasspectra.feature_dashboard.domain.util.ErrorMapper
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

@Singleton
class GetNetworkStateUseCase @Inject constructor(
    private val repository: SystemRepository,
    private val errorMapper: ErrorMapper,
    @param:ApplicationScope private val scope: CoroutineScope
) {
    val flow: SharedFlow<Result<NetworkState>> =
        flow {
            while (true) {
                try {
                    val data = repository.getNetworkStats()
                    emit(Result.Success(data))
                } catch (e: Exception) {
                    emit(Result.Error(errorMapper.map(e)))
                }

                delay(30000)
            }
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(30000),
            replay = 1
        )
}