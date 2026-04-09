package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.systems

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.TextPrimary
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.feature_dashboard.domain.model.SystemStats
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.SectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.ErrorCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LoadingCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toUiMessage
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.SystemViewModel

@Composable
fun SystemsScreen(viewModel: SystemViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    StatSection(state.sysInfo, { viewModel.loadSystemInfo() })
}

@Composable
fun StatSection(state: SectionState<SystemStats>, onRetry: () -> Unit) {
    when {
        state.isLoading -> {
            LoadingCard(text = "Loading System Stats...")
        }

        state.error != null -> {
            ErrorCard(
                title = "Error",
                message = state.error.toUiMessage(),
                onRetry = onRetry
            )
        }

        state.data != null -> {
            SystemStatCard(state.data)

        }
    }
}

@Composable
fun SystemStatCard(systemStats: SystemStats) {
    val uptime = "${systemStats.uptimeDays} Days, ${systemStats.uptimeHours} Hours, ${systemStats.uptimeMinutes} Minutes, ${systemStats.uptimeSeconds} Seconds"
    Column(modifier = Modifier.fillMaxSize().padding(16.dp). verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = "System Stats", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Accent, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Hostname :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.hostname, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "Uptime :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = uptime, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "CPU :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.cpuName, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Total Cores :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.totalCores.toString(), color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "Physical Cores :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.physicalCores.toString(), color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "Total Installed Memory :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = "${"%.1f".format(systemStats.totalInstalledMemory)} GB", color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "System Version :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.systemVersion, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "TimeZone :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.timeZone, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "Model :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.systemName, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "Serial :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.systemSerial, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Text(text = "Manufacturer :", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
            Text(text = systemStats.systemManufacturer, color = TextSecondary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f))
        }
    }


}