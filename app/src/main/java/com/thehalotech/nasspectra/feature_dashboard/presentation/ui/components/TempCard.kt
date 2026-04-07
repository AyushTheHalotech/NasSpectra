package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.Critical
import com.thehalotech.nasspectra.core.ui.theme.Surface2
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.core.ui.theme.Warning

@Composable
fun TempCard(
    modifier: Modifier = Modifier,
    label: String,
    tempC: Float,
    icon: ImageVector
) {
    val color = when {
        tempC > 70 -> Critical
        tempC > 55 -> Warning
        else       -> Accent
    }
    ObsidianCard(modifier) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(icon, null, tint = color.copy(.8f), modifier = Modifier.size(15.dp))
                Text(label, color = TextSecondary, fontSize = 10.sp, letterSpacing = 2.sp)
            }
            Text("${tempC.toInt()}°C", color = color, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            // Mini temp bar
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Surface2, RoundedCornerShape(2.dp))
            ) {
                val animW by animateFloatAsState((tempC / 100f).coerceIn(0f, 1f), tween(600), label = "tw")
                Box(
                    Modifier
                        .fillMaxWidth(animW)
                        .fillMaxHeight()
                        .background(Brush.horizontalGradient(listOf(color.copy(.4f), color)), RoundedCornerShape(2.dp))
                )
            }
        }
    }
}