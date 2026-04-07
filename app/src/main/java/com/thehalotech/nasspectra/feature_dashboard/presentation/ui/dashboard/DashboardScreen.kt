package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.dashboard

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.Critical
import com.thehalotech.nasspectra.core.ui.theme.CyanNeon
import com.thehalotech.nasspectra.core.ui.theme.TextPrimary
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.core.ui.theme.Warning
import com.thehalotech.nasspectra.feature_dashboard.domain.model.CpuTempsState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.NetworkState
import com.thehalotech.nasspectra.feature_dashboard.domain.model.PoolState
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.SectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.StatSectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.TempSectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.ErrorCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.GaugeCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.HealthStatus
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LoadingCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.StatCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.SystemIconView
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.SystemIcon
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.findDiskMeanTemp
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toUiMessage
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SystemSection(
            state = state.statSection,
            onRetry = { viewModel.loadReportingData() }
        )

        NetworkSection(
            state = state.network,
            onRetry = { viewModel.observeNetwork() }
        )

        TempSection(
            tempsState = state.tempSection,
            onRetry = { viewModel.loadReportingData() }
        )

        PoolSection(
            state = state.pools,
            onRetry = { viewModel.observePoolState() }
        )

    }
}

@Composable
fun SystemSection(
    state: StatSectionState,
    onRetry: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when {
            state.cpuUsage.isLoading -> LoadingCard("Loading CPU Usage...", modifier = Modifier.weight(1f))
            state.cpuUsage.error != null -> ErrorCard(
                modifier = Modifier.weight(1f),
                title = "Error",
                message = state.cpuUsage.error.toUiMessage(),
                onRetry = onRetry
            )

            state.cpuUsage.data != null -> {
                GaugeCard(
                    modifier = Modifier.weight(1f),
                    label = "CPU Usage",
                    icon = {SystemIconView(type = SystemIcon.CPU, size = 16.dp)},
                    value = state.cpuUsage.data.cpu.toFloat(),
                    unit = "%",
                    accentColor = Accent,
                    sparkline = null
                )
            }
        }

        when {
            state.memUsage.isLoading -> LoadingCard("Loading CPU Usage...", modifier = Modifier.weight(1f))
            state.memUsage.error != null -> ErrorCard(
                modifier = Modifier.weight(1f),
                title = "Error",
                message = state.memUsage.error.toUiMessage(),
                onRetry = onRetry
            )

            state.memUsage.data != null -> {
                val totalMem = state.memUsage.data.totalMem
                val usedMem = state.memUsage.data.usedMem
                val usedFraction = (usedMem / totalMem) * 100
                GaugeCard(
                    modifier = Modifier.weight(1f),
                    label = "RAM Usage",
                    icon = {SystemIconView(type = SystemIcon.RAM, size = 16.dp)},
                    value = usedFraction.toFloat(),
                    unit = "%",
                    accentColor = Accent,
                    sparkline = null
                )
            }
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
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                SystemIconView(type =SystemIcon.NETWORK, size = 32.dp)
                Text(text= networkInfo.name, color = TextPrimary, fontSize = 16.sp, letterSpacing = 1.sp, fontWeight = FontWeight.SemiBold)
                HealthStatus(health = when(networkInfo.linkState){"LINK_STATE_UP" -> "Healthy" else -> "Unhealthy"}
                    , modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            val address = networkInfo.iNetAddress

            Text(text = "INET :: $address", color = TextSecondary, fontSize = 14.sp, letterSpacing = 1.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
fun PoolSection(
    state: SectionState<List<PoolState>>,
    onRetry: () -> Unit
) {
    when {
        state.isLoading -> {
            LoadingCard(text = "Loading pool data...")
        }
        state.error != null -> {
            ErrorCard(
                title = "Error",
                message = state.error.toUiMessage(),
                onRetry = onRetry
            )
        }
        state.data != null -> {
            PoolData(state.data)
        }
    }
}

@Composable
fun PoolData(poolInfo: List<PoolState>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        poolInfo.forEach { pool ->
            PoolCard(pool)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun PoolCard(pool: PoolState) {
    StatCard {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)){
            SystemIconView(type = SystemIcon.FOLDER, size = 32.dp)
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = toUpperCase(pool.name),
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${"%.1f".format(pool.allocated)}GB / ${"%.1f".format(pool.size)}GB",
                        color = TextSecondary,
                        fontSize = 12.sp,
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                val progress = (pool.allocated / pool.size).toFloat().coerceIn(0f, 1f)

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(50)),
                    color = Color(0xFF00C9A7),
                    trackColor = Color(0xFF2D3547)
                )
            }

            HealthStatus(pool.health_calculated, modifier = Modifier.size(18.dp))

        }
    }

}

@Composable
fun TempSection(
    tempsState: TempSectionState,
    onRetry: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

        StatCard(modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.padding(10.dp)) {
                when {
                    tempsState.cpuTempState.isLoading -> LoadingCard("Loading CPU temps...")
                    tempsState.cpuTempState.error != null -> ErrorCard(
                        title = "Error",
                        message = tempsState.cpuTempState.error.toUiMessage(),
                        onRetry = onRetry
                    )

                    tempsState.cpuTempState.data != null -> {
                        CpuTempData(tempsState.cpuTempState.data)
                        Spacer(modifier = Modifier.height(8.dp))
                        TempProgressBar(tempValue = tempsState.cpuTempState.data.cpu)  // ← bar
                    }
                }
            }
        }

        StatCard(modifier = Modifier.weight(1f)) {

            Column(modifier = Modifier.padding(10.dp)) {
                when {
                    tempsState.diskTempState.isLoading -> LoadingCard("Loading Disk temps...")
                    tempsState.diskTempState.error != null -> ErrorCard(
                        title = "Error",
                        message = tempsState.diskTempState.error.toUiMessage(),
                        onRetry = onRetry
                    )

                    tempsState.diskTempState.data != null -> {
                        val meanTemp = findDiskMeanTemp(tempsState.diskTempState.data)
                        DiskTempData(meanTemp)
                        Spacer(modifier = Modifier.height(8.dp))
                        TempProgressBar(tempValue = meanTemp)  // ← bar
                    }
                }
            }
        }

    }
}

@Composable
fun CpuTempData(cputemp: CpuTempsState) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            SystemIconView(type = SystemIcon.CPU, size = 20.dp)
            Text(text = "CPU Temp", color = TextSecondary, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
        val color = when {
            cputemp.cpu < 65 -> CyanNeon
            cputemp.cpu < 80 -> Warning
            else -> Critical
        }
        Text(
            text = "%.1f".format(cputemp.cpu),
            color = color,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

    }
}

@Composable
fun DiskTempData(diskTemp: Double) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            SystemIconView(type = SystemIcon.DISK, size = 20.dp)
            Text(text = "Disk Temp", color = TextSecondary, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
        val color = when {
            diskTemp < 65 -> CyanNeon
            diskTemp < 80 -> Warning
            else -> Critical
        }
        Text(
            text = "%.1f".format(diskTemp),
            color = color,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )


    }
}

@Composable
fun TempProgressBar(
    tempValue: Double,
    maxTemp: Double = 100.0,
) {
    val progress = (tempValue / maxTemp).toFloat().coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "tempProgress"
    )

    val barColor by animateColorAsState(
        targetValue = when {
            progress < 0.65f -> CyanNeon   // cool — teal
            progress < 0.80f -> Warning  // warm — amber
            else -> Critical              // hot  — red
        },
        animationSpec = tween(durationMillis = 800),
        label = "tempColor"
    )

    LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(50)),
        color = barColor,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
}

