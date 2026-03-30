package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TrackGauge(progress: Float, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val stroke = 10.dp.toPx()
        val startAngle = 135f
        val sweepMax  = 270f
        val inset = stroke / 2f
        val rect = Rect(inset, inset, size.width - inset, size.height - inset)

        // Track
        drawArc(
            color = color.copy(alpha = .12f),
            startAngle = startAngle, sweepAngle = sweepMax,
            useCenter = false,
            topLeft = rect.topLeft, size = rect.size,
            style = Stroke(stroke, cap = StrokeCap.Round)
        )
        // Fill
        drawArc(
            brush = Brush.sweepGradient(
                listOf(color.copy(.6f), color),
                center = Offset(size.width / 2, size.height / 2)
            ),
            startAngle = startAngle, sweepAngle = sweepMax * progress,
            useCenter = false,
            topLeft = rect.topLeft, size = rect.size,
            style = Stroke(stroke, cap = StrokeCap.Round)
        )
        // Glow tip
        if (progress > 0.02f) {
            val angle = Math.toRadians((startAngle + sweepMax * progress).toDouble())
            val cx = size.width / 2 + ((size.width / 2 - inset) * cos(angle)).toFloat()
            val cy = size.height / 2 + ((size.height / 2 - inset) * sin(angle)).toFloat()
            drawCircle(color.copy(.5f), stroke * 1.2f, Offset(cx, cy))
            drawCircle(color, stroke * .55f, Offset(cx, cy))
        }
    }
}