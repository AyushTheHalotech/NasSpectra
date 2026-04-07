package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.IconState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.SystemIcon

@Composable
fun HealthStatus(health: String, modifier: Modifier) {
    when (health) {
        "Healthy"  -> {
            SystemIconView(type = SystemIcon.HEALTH, state = IconState.Healthy, size = 18.dp)
        }
        else -> {
            SystemIconView(type = SystemIcon.HEALTH, state = IconState.Warning, size = 18.dp)
        }
    }
}