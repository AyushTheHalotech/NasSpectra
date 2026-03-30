package com.thehalotech.nasspectra.feature_dashboard.di

import com.thehalotech.nasspectra.feature_dashboard.data.repository.SystemRepositoryImpl
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.SystemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DashboardModule {

    @Binds
    @Singleton
    abstract fun bindSystemRepository(
        systemRepositoryImpl: SystemRepositoryImpl
    ): SystemRepository
}