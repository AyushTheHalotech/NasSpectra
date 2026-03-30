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
import com.thehalotech.nasspectra.core.ui.theme.Surface0
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.DashboardState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.KineticBottomNav
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.TopBar
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.dashboard.DashboardScreen
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.DashboardViewModel

@Preview
@Composable
fun MainScreen(viewModel: DashboardViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner.lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> viewModel.startPolling()
                Lifecycle.Event.ON_STOP -> viewModel.stopPolling()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        containerColor = Surface0,
        bottomBar = { KineticBottomNav(selectedTab, { selectedTab = it }) },
        topBar = { TopBar() }

    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(color = Surface0)
        ) {
            DashboardScreen(state, onRetrySystem = {viewModel.loadSystemStats()}, onRetryNetwork = {viewModel.loadNetworkStats()})
        }

    }
}