package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thehalotech.nasspectra.core.ui.theme.Surface0
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.DashboardState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.appscreen.AppScreen
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.KineticBottomNav
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.TopBar
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.dashboard.DashboardScreen
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.storage.StorageScreen
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.DashboardViewModel
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.MainViewModel

@Preview
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    var uptime = ""
    when {
        state.systemState.isLoading -> {}
        state.systemState.error != null -> {viewModel.loadSystemStats()}
        state.systemState.data != null -> {
            val data = state.systemState.data
            val uptimeHours = data?.uptimeHours
            val uptimeDays = data?.uptimeDays
            uptime = "$uptimeDays Days $uptimeHours Hours"
        }
    }
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Surface0,
        bottomBar = { KineticBottomNav(selectedTab, { selectedTab = it }) },
        topBar = { TopBar(uptime = uptime) }

    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(color = Surface0)
        ) {
            when (selectedTab) {
                0 -> DashboardScreen()
                1 -> StorageScreen()
                2 -> AppScreen()
            }

        }

    }
}