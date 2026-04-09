package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thehalotech.nasspectra.core.ui.theme.Border
import com.thehalotech.nasspectra.core.ui.theme.ElectricCyan
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.SystemIcon

@Composable
fun KineticBottomNav(selected: Int, onSelect: (Int) -> Unit) {
    val items = listOf(
        Pair("Overview", SystemIcon.DASHBOARD),
        Pair("Storage",  SystemIcon.STORAGE),
        Pair("Apps",  SystemIcon.APPS),
        Pair("Systems",  SystemIcon.CPU),
    )
    Box(
        Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(Color.Transparent, Color(0xE50A0A0A))),
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(
                    Color(0xCC161616),
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .border(
                    1.dp,
                    Border,
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { idx, item ->
                val (label, icon) = item
                val isActive = idx == selected
                val tint by animateColorAsState(
                    if (isActive) ElectricCyan else TextSecondary,
                    tween(250),
                    label = "nav$idx"
                )
                Column(
                    Modifier
                        .clickable { onSelect(idx) }
                        .padding(horizontal = 20.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        SystemIconView(type = icon, size = 36.dp, color = tint)
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(label, color = tint, fontSize = 10.sp, fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal)
                }
            }
        }
    }
}