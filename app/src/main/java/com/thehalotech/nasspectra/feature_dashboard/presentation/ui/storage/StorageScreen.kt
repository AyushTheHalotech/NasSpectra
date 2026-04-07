package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.storage

import android.icu.lang.UCharacter.toUpperCase
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.thehalotech.nasspectra.core.ui.theme.Critical
import com.thehalotech.nasspectra.core.ui.theme.CyanNeon
import com.thehalotech.nasspectra.core.ui.theme.GreenNeon
import com.thehalotech.nasspectra.core.ui.theme.TextPrimary
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.core.ui.theme.Warning
import com.thehalotech.nasspectra.feature_dashboard.domain.model.DiskInfo
import com.thehalotech.nasspectra.feature_dashboard.domain.model.PoolState
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.SectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.ErrorCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.HealthStatus
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LoadingCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.StatCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.SystemIconView
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.SystemIcon
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toUiMessage
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.StorageViewModel

@Composable
fun StorageScreen(viewModel: StorageViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val diskState = state.disks
    val poolState = state.pools

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            text = "Pools",
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        PoolSection_Storage(
            state = poolState,
            onRetry = { viewModel.observePools()}
        )

        Text(
            text = "Disks",
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        DiskSection_Storage(
            state = diskState,
            onRetry = { viewModel.observeStorage()}
        )

    }

}

@Composable
fun PoolSection_Storage(
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
            Pool_Data(state.data)
        }
    }
}

@Composable
fun Pool_Data(poolInfo: List<PoolState>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        poolInfo.forEach { pool ->
            Pool_Card(pool)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}

@Composable
fun Pool_Card(poolState: PoolState) {
    val usedStorage = (poolState.allocated / poolState.size) * 100

    StatCard {
        Column(modifier = Modifier.padding(16.dp)) {
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
                            text = toUpperCase(poolState.name),
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${"%.1f".format(poolState.allocated)}GB / ${"%.1f".format(poolState.size)}GB",
                            color = TextSecondary,
                            fontSize = 12.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    val progress = (poolState.allocated / poolState.size).toFloat().coerceIn(0f, 1f)

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

                HealthStatus(poolState.health_calculated, modifier = Modifier.size(18.dp))

            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Status:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Used Storage:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Free Space:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = poolState.status,
                        color = when (poolState.status) {
                            "ONLINE" -> GreenNeon
                            else -> Warning
                        },
                        fontSize = 12.sp
                    )

                    Text(
                        text = "${"%.1f".format(usedStorage)} %",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "${"%.1f".format(poolState.free)} GB",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }

                VerticalDivider(modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                    color = TextSecondary)
                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Disk:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Device:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Scrub Errors",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = poolState.disk,
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Text(
                        text = poolState.device,
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Text(
                        text = poolState.scan_errors.toString(),
                        color = when(poolState.scan_errors) {
                            0 -> GreenNeon
                            else -> Warning
                        },
                        fontSize = 12.sp
                    )
                }

            }

        }
    }

}

@Composable
fun DiskSection_Storage(
    state: SectionState<List<DiskInfo>>,
    onRetry: () -> Unit
) {
    when {
        state.isLoading -> {
            LoadingCard(text = "Loading Disk data...")
        }
        state.error != null -> {
            ErrorCard(
                title = "Error",
                message = state.error.toUiMessage(),
                onRetry = onRetry
            )
        }
        state.data != null -> {
            Disk_Data(state.data)
        }
    }

}

@Composable
fun Disk_Data(diskInfo: List<DiskInfo>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        diskInfo.forEach { disk ->
            Disk_Card(disk)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun Disk_Card(diskState: DiskInfo) {

    val tempDouble = diskState.temperature?: -1.0

    val tempColor = when {
        tempDouble == -1.0 -> TextSecondary
        tempDouble < 65 -> GreenNeon
        tempDouble < 80 -> Warning
        else -> Critical
    }

    val temperatureText = diskState.temperature
        ?.let { "${"%.1f".format(it)} °C" }
        ?: "Unknown"

    val isBootDrive = diskState.imported_zpool.startsWith("boot")
    val typeText = if (isBootDrive) "${diskState.type} (Boot Drive)" else diskState.type ?: "HDD"

    StatCard {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                SystemIconView(type = SystemIcon.DISK, size = 32.dp)
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = typeText,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold)

                    Text(
                        text = diskState.model?: "Unknown Model",
                        color = TextPrimary,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold)
                }
                Text(
                    text = "${"%.1f".format(diskState.size)} GB",
                    color = TextPrimary,
                    fontSize = 12.sp)
            }

            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Rotation Rate:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Device Name:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                }
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = diskState.rotationrate.toString(),
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                    Text(
                        text = diskState.devname,
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                }
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    Text(
                        text = "In Pool:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Current Temp:",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )


                }
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    Text(
                        text = diskState.imported_zpool,
                        color = TextSecondary,
                        fontSize = 10.sp
                    )

                    Text(
                        text = temperatureText,
                        fontSize = 10.sp,
                        color = tempColor
                    )

                }
            }

        }

    }
}