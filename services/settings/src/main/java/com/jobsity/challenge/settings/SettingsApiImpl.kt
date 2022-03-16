package com.jobsity.challenge.settings

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

internal class SettingsApiImpl @Inject constructor(private val prefService: SharedPreferenceService) :
    SettingsApi {

    override fun isProvisioned(): Boolean =
        prefService.getEncryptedSharedPreferences().getBoolean(IS_PROVISIONED_KEY, false)

    override fun setIsProvisioned(newValue: Boolean) =
        prefService.getEncryptedSharedPreferences().edit()
            .putBoolean(IS_PROVISIONED_KEY, newValue)
            .apply()

    override fun isProtected(): Boolean =
        prefService.getEncryptedSharedPreferences().getBoolean(IS_SECURED_KEY, false)

    override fun setPinCode(pinCode: String) {
        prefService.getEncryptedSharedPreferences().edit().apply() {
            putString(PIN_VALUE_KEY, pinCode)
            putBoolean(IS_SECURED_KEY, true)
        }.apply()
    }

    override fun useBiometrics(): Boolean =
        prefService.getEncryptedSharedPreferences().getBoolean(USES_BIOMETRICS_KEY, false)

    override fun setUseBiometrics(useBiometrics: Boolean) =
        prefService.getEncryptedSharedPreferences().edit()
            .putBoolean(USES_BIOMETRICS_KEY, useBiometrics)
            .apply()

    override fun matches(pinCode: String): Boolean =
        pinCode == prefService.getEncryptedSharedPreferences().getString(PIN_VALUE_KEY, null)

    companion object {

        const val IS_PROVISIONED_KEY = "settings_is_provisioned"

        const val IS_SECURED_KEY = "settings_is_secured"

        const val PIN_VALUE_KEY = "settings_pin_code"

        const val USES_BIOMETRICS_KEY = "settings_uses_biometrics"
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsApiProvider {
    @Singleton
    @Binds
    internal abstract fun getSharedPreference(impl: SettingsApiImpl): SettingsApi
}

