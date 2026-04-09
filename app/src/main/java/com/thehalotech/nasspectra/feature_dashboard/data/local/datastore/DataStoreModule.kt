package com.thehalotech.nasspectra.feature_dashboard.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(
    name = "nasspectra_prefs"
)