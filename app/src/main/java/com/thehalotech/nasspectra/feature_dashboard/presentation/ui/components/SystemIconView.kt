package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components


import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thehalotech.nasspectra.R
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.Critical
import com.thehalotech.nasspectra.core.ui.theme.GreenNeon
import com.thehalotech.nasspectra.core.ui.theme.Warning
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.IconState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.SystemIcon

@Composable
fun SystemIconView(
    modifier: Modifier = Modifier,
    type: SystemIcon,
    state: IconState = IconState.Default,
    size: Dp = 20.dp,
    color: Color = Accent
) {
    val painter = getIconPainter(type, state)

    val tint = getIconTint(state, color)

    Icon(
        painter = painter,
        contentDescription = type.name,
        modifier = modifier.size(size),
        tint = tint
    )
}

@Composable
private fun getIconPainter(type: SystemIcon, state: IconState): Painter {
    return when (type) {
        SystemIcon.CPU -> painterResource(R.drawable.ic_cpu)
        SystemIcon.RAM -> painterResource(R.drawable.ic_memory)
        SystemIcon.DISK -> painterResource(R.drawable.ic_hard_disk)
        SystemIcon.TEMPERATURE -> painterResource(R.drawable.ic_device_thermostat)
        SystemIcon.NETWORK -> painterResource(R.drawable.ic_network_check)
        SystemIcon.FOLDER -> painterResource(R.drawable.ic_folder)
        SystemIcon.STORAGE -> painterResource(R.drawable.ic_storage)
        SystemIcon.DASHBOARD -> painterResource(R.drawable.ic_dashboard)
        SystemIcon.APPS -> painterResource(R.drawable.ic_apps)
        SystemIcon.APPS_UPDATE -> painterResource(R.drawable.ic_apps_update)

        SystemIcon.HEALTH -> {
            when (state) {
                IconState.Healthy -> painterResource(R.drawable.ic_check_circle)
                IconState.Warning -> painterResource(R.drawable.ic_warning)
                IconState.Critical -> painterResource(R.drawable.ic_critical)
                else -> painterResource(R.drawable.ic_info)
            }
        }
    }
}

@Composable
private fun getIconTint(state: IconState, color: Color): Color {
    return when (state) {
        IconState.Healthy -> GreenNeon  // Green
        IconState.Warning -> Warning   // Amber
        IconState.Critical -> Critical  // Red
        else -> color
    }
}