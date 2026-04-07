package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import com.thehalotech.nasspectra.application.ApplicationScope
import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.ErrorMapper
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn


@Singleton
class ObserveDiskStatesUseCase @Inject constructor(
    private val systemRepository: SystemRepository,
    private val errorMapper: ErrorMapper,
   @param:ApplicationScope private val scope: CoroutineScope
) {
    val flow: SharedFlow<Result<List<DiskInfo>>> =
        flow {
            while (true) {
                try {
                    val data = systemRepository.getDisksWithTemperature()
                    emit(Result.Success(data))
                } catch (e: Exception) {
                    emit(Result.Error(errorMapper.map(e)))
                }

                delay(5000)
            }
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )
}