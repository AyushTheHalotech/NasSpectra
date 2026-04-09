package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thehalotech.nasspectra.core.ui.theme.Critical
import com.thehalotech.nasspectra.core.ui.theme.GreenNeon
import com.thehalotech.nasspectra.core.ui.theme.LandingBackground
import com.thehalotech.nasspectra.core.ui.theme.LandingError
import com.thehalotech.nasspectra.core.ui.theme.LandingOutline
import com.thehalotech.nasspectra.core.ui.theme.LandingPrimary
import com.thehalotech.nasspectra.core.ui.theme.LandingSurface
import com.thehalotech.nasspectra.core.ui.theme.LandingSurfaceVariant
import com.thehalotech.nasspectra.core.ui.theme.Warning
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.LandingState

@Composable
fun LandingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LandingBackground)
            .border(0.5.dp, LandingPrimary.copy(alpha = 0.2f))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Terminal, contentDescription = null, tint = LandingPrimary, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(12.dp))
        Text("NAS_SPECTRA_v1.0", color = LandingPrimary, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, fontSize = 12.sp)
        Spacer(Modifier.weight(1f))
        Text("SYSTEM: OPERATIONAL", color = LandingPrimary.copy(alpha = 0.4f), fontSize = 10.sp, letterSpacing = 1.sp)
    }
}

@Composable
fun LandingInputFields(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false) {
    var passwordVisible by remember { mutableStateOf(false) }
    Column {
        Text(label, color = LandingPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray.copy(alpha = 0.3f), fontFamily = FontFamily.Monospace) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =
                if (isPassword && !passwordVisible) PasswordVisualTransformation()
                else androidx.compose.ui.text.input.VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    Icon(Icons.Default.Visibility,
                        null,
                        tint = if (passwordVisible) LandingPrimary else Color.Gray.copy(0.4f),
                        modifier = Modifier.
                        clickable{ passwordVisible = !passwordVisible }
                    )
                } },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LandingSurfaceVariant.copy(0.5f),
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = LandingPrimary,
                unfocusedIndicatorColor = LandingOutline.copy(0.3f),
                cursorColor = LandingPrimary,
                focusedTextColor = Color.White
            )
        )
    }
}

@Composable
fun StatusPulseDot(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(800), repeatMode = RepeatMode.Reverse), label = ""
    )
    Box(modifier = Modifier.size(8.dp).alpha(alpha).background(color, CircleShape))
}

@Composable
fun LandingBackGroundEffects() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val dotSpacing = 24.dp.toPx()
        // Draw Dot Grid
        for (x in 0..size.width.toInt() step dotSpacing.toInt()) {
            for (y in 0..size.height.toInt() step dotSpacing.toInt()) {
                drawCircle(LandingPrimary.copy(alpha = 0.05f), radius = 1.dp.toPx(), center = Offset(x.toFloat(), y.toFloat()))
            }
        }
        // Draw Scanlines
        val scanlineHeight = 4.dp.toPx()
        for (y in 0..size.height.toInt() step (scanlineHeight * 2).toInt()) {
            drawRect(
                color = LandingPrimary.copy(alpha = 0.02f),
                topLeft = Offset(0f, y.toFloat()),
                size = androidx.compose.ui.geometry.Size(size.width, scanlineHeight)
            )
        }
    }
}

@Composable
fun LandingTerminalCard(
    state: LandingState,
    onIpChange: (String) -> Unit,
    onApiKeyChange: (String) -> Unit,
    onTestConnection: () -> Unit,
    onNext: () -> Unit
) {
    val statusText = when {
        state.isLoading -> "CONNECTING..."
        state.isConnected -> "CONNECTED"
        else -> "DISCONNECTED"
    }
    val statusColor = when {
        state.isLoading -> Warning
        state.isConnected -> GreenNeon
        else -> Critical
    }
    Box(modifier = Modifier.padding(24.dp)) {
        // Blur Glow Background
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(40.dp)
                .background(LandingPrimary.copy(alpha = 0.05f), CircleShape)
        )

        // Main Card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 450.dp)
                .drawBehind {
                    val stroke = 2.dp.toPx()
                    val length = 40.dp.toPx()
                    // Top-Right Bracket
                    drawLine(LandingPrimary.copy(0.4f), Offset(size.width - length, 0f), Offset(size.width, 0f), stroke)
                    drawLine(LandingPrimary.copy(0.4f), Offset(size.width, 0f), Offset(size.width, length), stroke)
                    // Bottom-Left Bracket
                    drawLine(LandingPrimary.copy(0.4f), Offset(0f, size.height - length), Offset(0f, size.height), stroke)
                    drawLine(LandingPrimary.copy(0.4f), Offset(0f, size.height), Offset(length, size.height), stroke)
                },
            color = LandingSurface.copy(alpha = 0.85f),
            shape = RoundedCornerShape(2.dp),
            border = BorderStroke(0.5.dp, LandingPrimary.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                // Status Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusPulseDot(statusColor)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "CONNECTION STATUS: $statusText",
                        color = statusColor.copy(alpha = 0.8f),
                        fontSize = 10.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.weight(1f))
                    Text("SEC_LEVEL_4", color = Color.Gray.copy(alpha = 0.5f), fontSize = 10.sp)
                }

                Spacer(Modifier.height(40.dp))

                Text("ACCESS_TERMINAL", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Verification required to establish uplink.", color = Color.Gray, fontSize = 14.sp)

                Spacer(Modifier.height(48.dp))

                LandingInputFields(
                    label = "IP ADDRESS",
                    value = state.ip,
                    onValueChange = onIpChange,
                    placeholder = "192.168.1.1")
                Spacer(Modifier.height(32.dp))
                LandingInputFields(
                    label = "ENCRYPTION KEY",
                    value = state.apiKey,
                    onValueChange = onApiKeyChange,
                    placeholder = "xxxx-xxxx-xxxx",
                    isPassword = true
                )

                Spacer(Modifier.height(48.dp))

                // Action Buttons
                Button(
                    onClick = onNext,
                    enabled = state.isConnected,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (state.isConnected) LandingPrimary else LandingPrimary.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("NEXT", color = LandingBackground, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, tint = LandingBackground, modifier = Modifier.size(16.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))
                val isApiKeyValid = state.apiKey.isNotBlank() && !hasInvalidApiKeyChars(state.apiKey)
                val isIpValid = isValidIP(state.ip)

                OutlinedButton(
                    onClick = onTestConnection,
                    enabled = isApiKeyValid && isIpValid,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    border = BorderStroke(1.dp, LandingPrimary.copy(alpha = 0.2f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = LandingPrimary),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Icon(Icons.Default.CellTower, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("TEST CONNECTION", fontWeight = FontWeight.Bold)
                }

                if (hasInvalidApiKeyChars(state.apiKey)) {
                    Text(
                        "API key cannot contain line breaks",
                        color = LandingError,
                        fontSize = 12.sp
                    )
                }

                state.error?.let {
                    Text(it, color = LandingError, fontSize = 12.sp)
                }
            }
        }
    }
}

fun isValidIP(ip: String): Boolean {
    val regex = Regex(
        "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.|$)){4}$"
    )
    return regex.matches(ip)
}

fun hasInvalidApiKeyChars(apiKey: String): Boolean {
    return apiKey.contains("\n") || apiKey.contains("\r")
}