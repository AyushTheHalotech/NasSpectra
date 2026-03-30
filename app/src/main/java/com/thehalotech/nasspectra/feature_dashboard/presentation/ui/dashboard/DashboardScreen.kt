package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.dashboard

import android.R.attr.fontWeight
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.NetworkWifi
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.Critical
import com.thehalotech.nasspectra.core.ui.theme.GreenNeon
import com.thehalotech.nasspectra.core.ui.theme.TextPrimary
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.core.ui.theme.WarningAmber
import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.DashboardState
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.SectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.ErrorCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.GaugeCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LoadingCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.StatCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.TempCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toUiMessage

@Composable
fun DashboardScreen(state: DashboardState,
                    onRetrySystem: () -> Unit,
                    onRetryNetwork: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SystemSection(
            state = state.system,
            onRetry = onRetrySystem
        )

        NetworkSection(
            state = state.network,
            onRetry = onRetryNetwork
        )

    }
}

@Composable
fun SystemSection(
    state: SectionState<SystemStats>,
    onRetry: () -> Unit
) {
    when {
        state.isLoading -> {LoadingCard(text = "Loading system data...")
        }
        state.error != null -> {
            ErrorCard(
                title = "Error",
                message = state.error.toUiMessage(),
                onRetry = onRetry
            )
        }

        state.data != null -> {
            SystemData(state.data)
        }
    }
}




@Composable
fun SystemData(systemInfo: SystemStats){

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GaugeCard(
                modifier = Modifier.weight(1f),
                label = "CPU",
                icon = Icons.Rounded.Memory,
                value = systemInfo.cpuUsagePercent.toFloat(),
                unit = "%",
                accentColor = Accent,
                sparkline = null
            )
            GaugeCard(
                modifier = Modifier.weight(1f),
                label = "RAM",
                icon = Icons.Rounded.Storage,
                value = (systemInfo.totalMemoryGb/2).toFloat(),
                unit = "%",
                accentColor = Accent,
                sparkline = null
            )
        }
    }

}

@Composable
fun NetworkSection(
    state: SectionState<NetworkState>,
    onRetry: () -> Unit
) {
    when {
        state.isLoading -> {
            LoadingCard(text = "Loading network data...")

        }

        state.error != null -> {
            ErrorCard(
                title = "Error",
                message = state.error.toUiMessage(),
                onRetry = onRetry)
        }

        state.data != null -> {
            NetworkData(state.data)
        }
    }
}

@Composable
fun NetworkData(networkInfo: NetworkState) {
    StatCard {
        Column(modifier = Modifier
            .fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.weight(1f),
                    text= networkInfo.name, color = TextPrimary, fontSize = 10.sp, letterSpacing = 1.sp,
                    textAlign = TextAlign.Start)
                Text(modifier = Modifier.weight(1f),
                    text = networkInfo.linkState,
                    color = when(networkInfo.linkState) {
                        "LINK_STATE_UP" -> GreenNeon
                        else -> WarningAmber
                    },
                    fontSize = 10.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.End)
            }
            Spacer(modifier = Modifier.height(8.dp))
            val address = networkInfo.iNetAddress

            Text(text = "INET :: $address", color = TextPrimary, fontSize = 18.sp, letterSpacing = 1.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
        }

    }

}


@Composable
fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Loading system data...")
    }
}

@Composable
fun ErrorView(
    message: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message ?: "Something went wrong",
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}