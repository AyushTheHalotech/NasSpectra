package com.thehalotech.nasspectra

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.AccentSoft
import com.thehalotech.nasspectra.core.ui.theme.Critical
import com.thehalotech.nasspectra.core.ui.theme.Surface0
import com.thehalotech.nasspectra.core.ui.theme.Surface1
import com.thehalotech.nasspectra.core.ui.theme.Surface2
import com.thehalotech.nasspectra.core.ui.theme.TextPrimary
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.core.ui.theme.Warning
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.KineticBottomNav
import kotlin.math.*

// ─── Color Tokens ────────────────────────────────────────────────────────────


// ─── Fake live data state ─────────────────────────────────────────────────────
data class NasState(
    val cpuPercent: Float,
    val ramPercent: Float,
    val storagePools: List<StoragePool>,
    val networkRxMbps: Float,
    val networkTxMbps: Float,
    val cpuTempC: Float,
    val hddTempC: Float,
    val cpuSparkline: List<Float>,
    val netSparkline: List<Float>,
    val uptimeDays: Int,
    val uptimeHours: Int,
    val alerts: List<String>
)

data class StoragePool(val name: String, val usedTB: Float, val totalTB: Float, val healthy: Boolean)

// ─── Root Scaffold ─────────────────────────────────────────────────────────────
@Preview
@Composable
fun KineticObservatoryDashboard() {
    // Simulate ticking data
    var tick by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(2_000)
            tick++
        }
    }

    val state = remember(tick) {
        NasState(
            cpuPercent    = (28f + sin(tick * 0.7f) * 18f + (tick % 3) * 2f).coerceIn(5f, 95f),
            ramPercent    = (61f + cos(tick * 0.4f) * 8f).coerceIn(20f, 92f),
            storagePools  = listOf(
                StoragePool("DATA-1", 3.7f, 6f, true),
                StoragePool("BACKUP", 1.2f, 4f, true),
                StoragePool("PARITY", 0.4f, 2f, false)
            ),
            networkRxMbps = (120f + sin(tick * 1.1f) * 80f).coerceAtLeast(0f),
            networkTxMbps = (45f  + cos(tick * 0.9f) * 30f).coerceAtLeast(0f),
            cpuTempC      = (48f  + sin(tick * 0.5f) * 6f),
            hddTempC      = (38f  + cos(tick * 0.3f) * 3f),
            cpuSparkline  = List(20) { i -> (30f + sin((tick + i) * 0.6f) * 22f).coerceIn(5f, 95f) },
            netSparkline  = List(20) { i -> (100f + sin((tick + i) * 0.8f) * 70f).coerceAtLeast(0f) },
            uptimeDays    = 12,
            uptimeHours   = 7,
            alerts        = if (tick % 7 == 0) listOf("PARITY pool degraded – check drive 3") else emptyList()
        )
    }

    var selectedTab by remember { mutableIntStateOf(0) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Surface0)
    ) {
        Column(Modifier.fillMaxSize()) {
            TopStatusBar(state)
            Box(Modifier.weight(1f)) {
                when (selectedTab) {
                    0 -> OverviewScreen(state)
                    1 -> StorageScreen(state)
                    2 -> NetworkScreen(state)
                    3 -> ThermalScreen(state)
                }
            }
            KineticBottomNav(selectedTab) { selectedTab = it }
        }
    }
}

// ─── Top Status Bar ───────────────────────────────────────────────────────────
@Composable
fun TopStatusBar(state: NasState) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0D1A1C), Surface0)
                )
            )
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "KINETIC OBSERVATORY",
                    color = Accent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 3.sp
                )
                Text(
                    "NAS Command Center",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PulseDot(color = Accent)
                Text(
                    "ONLINE  ${state.uptimeDays}d ${state.uptimeHours}h",
                    color = AccentSoft,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
            }
        }
        // Alert banner
        if (state.alerts.isNotEmpty()) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 28.dp)
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFF2A1400), Color(0xFF1A0A00))),
                        RoundedCornerShape(6.dp)
                    )
                    .border(1.dp, Warning.copy(alpha = .4f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Text(
                    "⚠  ${state.alerts.first()}",
                    color = Warning,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ─── Pulse Dot ────────────────────────────────────────────────────────────────
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

// ─── Overview Screen ──────────────────────────────────────────────────────────
@Composable
fun OverviewScreen(state: NasState) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Spacer(Modifier.height(if (state.alerts.isNotEmpty()) 20.dp else 0.dp))

        // CPU + RAM side by side
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            GaugeCards(
                modifier = Modifier.weight(1f),
                label = "CPU",
                icon = Icons.Rounded.Memory,
                value = state.cpuPercent,
                unit = "%",
                accentColor = Accent,
                sparkline = state.cpuSparkline
            )
            GaugeCards(
                modifier = Modifier.weight(1f),
                label = "RAM",
                icon = Icons.Rounded.Storage,
                value = state.ramPercent,
                unit = "%",
                accentColor = Color(0xFF7C4DFF),
                sparkline = null
            )
        }

        // Network card
        NetworkCard(state)

        // Thermal row
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ThermalTile(Modifier.weight(1f), "CPU TEMP", state.cpuTempC, Icons.Rounded.Thermostat)
            ThermalTile(Modifier.weight(1f), "HDD TEMP", state.hddTempC, Icons.Rounded.Dns)
        }

        // Storage pools
        state.storagePools.forEach { pool ->
            StoragePoolRow(pool)
        }

        Spacer(Modifier.height(72.dp))
    }
}

// ─── Gauge Card ───────────────────────────────────────────────────────────────
@Composable
fun GaugeCards(
    modifier: Modifier = Modifier,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: Float,
    unit: String,
    accentColor: Color,
    sparkline: List<Float>?
) {
    val animValue by animateFloatAsState(
        value, animationSpec = tween(800, easing = EaseInOutCubic), label = "gauge"
    )

    ObsidianCard(modifier) {
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
                Icon(icon, null, tint = accentColor.copy(alpha = .7f), modifier = Modifier.size(16.dp))
            }
            Spacer(Modifier.height(16.dp))
            ArcGauge(animValue / 100f, accentColor, Modifier.size(110.dp))
            Spacer(Modifier.height(8.dp))
            Text(
                "${animValue.toInt()}$unit",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
            if (sparkline != null) {
                Spacer(Modifier.height(10.dp))
                Sparkline(sparkline, accentColor, Modifier.fillMaxWidth().height(28.dp))
            }
        }
    }
}

// ─── Arc Gauge ────────────────────────────────────────────────────────────────
@Composable
fun ArcGauge(progress: Float, color: Color, modifier: Modifier = Modifier) {
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

// ─── Sparkline ────────────────────────────────────────────────────────────────
@Composable
fun Sparkline(data: List<Float>, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        if (data.size < 2) return@Canvas
        val max = data.max()
        val min = data.min()
        val range = (max - min).coerceAtLeast(1f)
        val step = size.width / (data.size - 1)

        val pts = data.mapIndexed { i, v ->
            Offset(i * step, size.height - (v - min) / range * size.height)
        }

        // Filled area
        val path = Path().apply {
            moveTo(pts.first().x, size.height)
            pts.forEach { lineTo(it.x, it.y) }
            lineTo(pts.last().x, size.height)
            close()
        }
        drawPath(path, Brush.verticalGradient(listOf(color.copy(.25f), Color.Transparent)))

        // Line
        val linePath = Path().apply {
            moveTo(pts.first().x, pts.first().y)
            pts.drop(1).forEach { lineTo(it.x, it.y) }
        }
        drawPath(linePath, color, style = Stroke(1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

// ─── Network Card ─────────────────────────────────────────────────────────────
@Composable
fun NetworkCard(state: NasState) {
    ObsidianCard {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("NETWORK", color = TextSecondary, fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Medium)
                Icon(Icons.Rounded.NetworkCheck, null, tint = Accent.copy(.7f), modifier = Modifier.size(16.dp))
            }
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Column(Modifier.weight(1f)) {
                    Text("↓ RX", color = AccentSoft, fontSize = 10.sp, letterSpacing = 1.sp)
                    Text("${state.networkRxMbps.toInt()} Mbps", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                Column(Modifier.weight(1f)) {
                    Text("↑ TX", color = Warning.copy(.8f), fontSize = 10.sp, letterSpacing = 1.sp)
                    Text("${state.networkTxMbps.toInt()} Mbps", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(Modifier.height(12.dp))
            Sparkline(state.netSparkline, Accent, Modifier.fillMaxWidth().height(36.dp))
        }
    }
}

// ─── Thermal Tile ─────────────────────────────────────────────────────────────
@Composable
fun ThermalTile(
    modifier: Modifier = Modifier,
    label: String,
    tempC: Float,
    icon: androidx.compose.ui.graphics.vector.ImageVector
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

// ─── Storage Pool Row ─────────────────────────────────────────────────────────
@Composable
fun StoragePoolRow(pool: StoragePool) {
    val usePct = pool.usedTB / pool.totalTB
    val barColor = when {
        !pool.healthy  -> Critical
        usePct > .85f  -> Warning
        else           -> Accent
    }
    val animUsePct by animateFloatAsState(usePct, tween(700), label = "sp")

    ObsidianCard {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Rounded.FolderOpen, null, tint = barColor.copy(.8f), modifier = Modifier.size(18.dp))
            Column(Modifier.weight(1f)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(pool.name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    Text(
                        "${pool.usedTB}TB / ${pool.totalTB}TB",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                Spacer(Modifier.height(6.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(Surface2, RoundedCornerShape(3.dp))
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(animUsePct)
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(listOf(barColor.copy(.5f), barColor)),
                                RoundedCornerShape(3.dp)
                            )
                    )
                }
            }
            // Health badge
            Box(
                Modifier
                    .background(
                        if (pool.healthy) Accent.copy(.12f) else Critical.copy(.12f),
                        RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    if (pool.healthy) "OK" else "WARN",
                    color = if (pool.healthy) Accent else Critical,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

// ─── Storage Screen ───────────────────────────────────────────────────────────
@Composable
fun StorageScreen(state: NasState) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(Modifier.height(4.dp))
        Text("STORAGE POOLS", color = TextSecondary, fontSize = 11.sp, letterSpacing = 3.sp)
        state.storagePools.forEach { StoragePoolRow(it) }
        Spacer(Modifier.height(12.dp))
        Text("CAPACITY OVERVIEW", color = TextSecondary, fontSize = 11.sp, letterSpacing = 3.sp)
        ObsidianCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                val totalUsed = state.storagePools.sumOf { it.usedTB.toDouble() }.toFloat()
                val totalCap  = state.storagePools.sumOf { it.totalTB.toDouble() }.toFloat()
                Text("${totalUsed}TB used of ${totalCap}TB", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))
                val pct by animateFloatAsState(totalUsed / totalCap, tween(800), label = "tot")
                Box(Modifier.fillMaxWidth().height(8.dp).background(Surface2, RoundedCornerShape(4.dp))) {
                    Box(
                        Modifier.fillMaxWidth(pct).fillMaxHeight()
                            .background(Brush.horizontalGradient(listOf(Accent.copy(.5f), Accent)), RoundedCornerShape(4.dp))
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text("${(pct * 100).toInt()}% utilized", color = TextSecondary, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(72.dp))
    }
}

// ─── Network Screen ───────────────────────────────────────────────────────────
@Composable
fun NetworkScreen(state: NasState) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(Modifier.height(4.dp))
        Text("NETWORK ACTIVITY", color = TextSecondary, fontSize = 11.sp, letterSpacing = 3.sp)
        NetworkCard(state)
        ObsidianCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("THROUGHPUT HISTORY", color = TextSecondary, fontSize = 10.sp, letterSpacing = 2.sp)
                Spacer(Modifier.height(12.dp))
                Sparkline(state.netSparkline, Accent, Modifier.fillMaxWidth().height(80.dp))
                Spacer(Modifier.height(6.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("RX Peak: ${state.netSparkline.maxOrNull()?.toInt()} Mbps", color = AccentSoft, fontSize = 11.sp)
                    Text("TX: ${state.networkTxMbps.toInt()} Mbps", color = Warning, fontSize = 11.sp)
                }
            }
        }
        Spacer(Modifier.height(72.dp))
    }
}

// ─── Thermal Screen ───────────────────────────────────────────────────────────
@Composable
fun ThermalScreen(state: NasState) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(Modifier.height(4.dp))
        Text("THERMAL MONITORING", color = TextSecondary, fontSize = 11.sp, letterSpacing = 3.sp)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ThermalTile(Modifier.weight(1f), "CPU TEMP",  state.cpuTempC, Icons.Rounded.Thermostat)
            ThermalTile(Modifier.weight(1f), "HDD TEMP",  state.hddTempC, Icons.Rounded.Dns)
        }
        ObsidianCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("TEMP LEGEND", color = TextSecondary, fontSize = 10.sp, letterSpacing = 2.sp)
                Spacer(Modifier.height(10.dp))
                listOf(
                    Triple("< 55°C", "Optimal", Accent),
                    Triple("55–70°C", "Warning", Warning),
                    Triple("> 70°C",  "Critical", Critical)
                ).forEach { (range, label, color) ->
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(Modifier.size(8.dp).background(color, CircleShape))
                            Text(label, color = TextPrimary, fontSize = 13.sp)
                        }
                        Text(range, color = TextSecondary, fontSize = 12.sp)
                    }
                }
            }
        }
        Spacer(Modifier.height(72.dp))
    }
}

// ─── Bottom Navigation Bar ────────────────────────────────────────────────────


// ─── Obsidian Card ────────────────────────────────────────────────────────────
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
