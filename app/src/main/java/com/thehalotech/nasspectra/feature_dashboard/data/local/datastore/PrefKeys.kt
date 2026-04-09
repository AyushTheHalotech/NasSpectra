package com.thehalotech.nasspectra.feature_dashboard.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object PrefKeys {
    val BASE_URL = stringPreferencesKey("base_url")
    val API_KEY = stringPreferencesKey("api_key")
}