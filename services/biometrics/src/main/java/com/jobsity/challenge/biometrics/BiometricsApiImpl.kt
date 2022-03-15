package com.jobsity.challenge.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class BiometricsApiImpl @Inject constructor(@ApplicationContext val appContext: Context) :
    BiometricsApi {

    override fun isFingerprintSupported(): Boolean = BiometricManager.from(appContext)
        .canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BiometricsApiProvider {
    @Singleton
    @Binds
    internal abstract fun getBiometrics(impl: BiometricsApiImpl): BiometricsApi
}

