package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.Surface1

@Composable
fun ObsidianCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(Surface1, Color(0xFF111111)),
                    start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                ),
                RoundedCornerShape(24.dp)
            )
            .border(
                1.dp,
                Brush.linearGradient(
                    listOf(Color(0xFF2A2A2A), Color(0xFF181818)),
                    start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                ),
                RoundedCornerShape(24.dp)
            )
    ) {
        // Subtle cyan glow at top-left corner
        Box(
            Modifier
                .align(Alignment.TopStart)
                .size(80.dp)
                .background(
                    Brush.radialGradient(listOf(Accent.copy(.05f), Color.Transparent)),
                    RoundedCornerShape(24.dp)
                )
        )
        content()
    }
}