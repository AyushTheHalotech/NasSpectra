package com.thehalotech.nasspectra.feature_dashboard.di

import com.thehalotech.nasspectra.feature_dashboard.data.repository.CredentialRepositoryImpl
import com.thehalotech.nasspectra.feature_dashboard.domain.repository.CredentialsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CredentialModule {

    @Binds
    @Singleton
    abstract fun bindCredentialsRepository(
        impl: CredentialRepositoryImpl
    ): CredentialsRepository
}