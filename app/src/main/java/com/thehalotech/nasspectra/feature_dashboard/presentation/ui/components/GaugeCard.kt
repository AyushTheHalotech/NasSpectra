package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thehalotech.nasspectra.core.ui.theme.TextPrimary
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary

@Composable
fun GaugeCard(
    modifier: Modifier = Modifier,
    label: String,
    icon: @Composable () -> Unit,
    value: Float,
    unit: String,
    accentColor: Color,
    sparkline: List<Float>?
) {
    val animValue by animateFloatAsState(
        value, animationSpec = tween(800, easing = EaseInOutCubic), label = "gauge"
    )

    StatCard(modifier) {
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(label, color = TextSecondary, fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Medium)
                icon()
            }
            Spacer(Modifier.height(16.dp))
            TrackGauge(animValue / 100f, accentColor, Modifier.size(110.dp))
            Spacer(Modifier.height(8.dp))
            Text(
                "${animValue.toInt()}$unit",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}