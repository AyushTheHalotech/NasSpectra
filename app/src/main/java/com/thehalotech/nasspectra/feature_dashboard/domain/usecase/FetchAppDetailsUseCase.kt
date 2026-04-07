package com.thehalotech.nasspectra.feature_dashboard.domain.usecase

import android.util.Log
import com.thehalotech.nasspectra.feature_dashboard.domain.model.AppInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import com.thehalotech.nasspectra.feature_dashboard.domain.util.ErrorMapper
import com.thehalotech.nasspectra.feature_dashboard.domain.util.Result
import javax.inject.Inject

class FetchAppDetailsUseCase @Inject constructor(
    private val repository: SystemRepository,
    private val errorMapper: ErrorMapper
){
    suspend operator fun invoke(): Result<List<AppInfo>> {
        return try {
            val data = repository.getAppDetails()
            Log.i("FetchAppDetailsUseCase", data.toString())
            Result.Success(data)
        } catch (e: Exception) {
            Log.e("FetchAppDetailsUseCase", e.toString())
            Result.Error(errorMapper.map(e))
        }
    }
}