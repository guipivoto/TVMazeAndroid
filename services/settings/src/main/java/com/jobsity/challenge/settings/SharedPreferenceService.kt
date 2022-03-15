package com.jobsity.challenge.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * API used to provided shared preference instances to other classes via dependency injection
 */
internal abstract class SharedPreferenceService {

    /**
     * Get an Encrypted version of the Shared Preference
     */
    abstract fun getEncryptedSharedPreferences(): SharedPreferences
}

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesProvider {
    @Provides
    internal fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferenceService {
        return object : SharedPreferenceService() {
            override fun getEncryptedSharedPreferences() = EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }
}