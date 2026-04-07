package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components


import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PulseDot(color: Color) {
    val inf = rememberInfiniteTransition(label = "pulse")
    val scale by inf.animateFloat(1f, 1.8f, infiniteRepeatable(tween(900, easing = EaseInOut), RepeatMode.Reverse), label = "s")
    val alpha by inf.animateFloat(.8f, .2f, infiniteRepeatable(tween(900, easing = EaseInOut), RepeatMode.Reverse), label = "a")
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(14.dp)) {
        Box(Modifier.size(14.dp * scale).background(color.copy(alpha = alpha * 0.3f), CircleShape))
        Box(Modifier.size(7.dp).background(color, CircleShape))
    }
}