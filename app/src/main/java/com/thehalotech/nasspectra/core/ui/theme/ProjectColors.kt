package com.thehalotech.nasspectra.core.ui.theme

import androidx.compose.ui.graphics.Color

// --- Base Theme Colors ---
val MidnightBackground = Color(0xFF020A0E) // The main OLED black/blue background
val CardSurface = Color(0xFF0A1216)        // Slightly lighter for bento cards
val CardBorder = Color(0xFF1A2A32)         // Subtle stroke for card boundaries

// --- Neon Accents (The "Glow") ---
val CyanNeon = Color(0xFF00E5FF)           // Primary data color (CPU Usage)
val GreenNeon = Color(0xFF2ECC71)          // Secondary data color (Memory/Connected)
val WarningAmber = Color(0xFFF1C40F)       // For high load alerts
val CriticalRed = Color(0xFFE74C3C)        // For system errors

// --- Text & Foreground ---
val TextPrimary = Color(0xFFFFFFFF)        // Pure white for main values
val TextSecondary = Color(0xFF94A3B8)      // Muted slate for labels and subtitles
val TextDisabled = Color(0xFF475569)       // For inactive elements

// --- Component Specifics ---
val GaugeTrack = Color(0xFF00E5FF).copy(alpha = 0.1f) // Faded track for circular gauges
val StatusPulsing = Color(0xFF00FF94)      // The bright green "CONNECTED" dot

// --- Material 3 Color Scheme Mapping ---
// You can use these in your Theme.kt setup
val DarkColorScheme = androidx.compose.material3.darkColorScheme(
    primary = CyanNeon,
    secondary = GreenNeon,
    background = MidnightBackground,
    surface = CardSurface,
    onPrimary = Color.Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    outline = CardBorder
)

val Surface0 = Color(0xFF0A0A0A) // True Black
val Surface1 = Color(0xFF161616) // Card backgrounds
val Surface2 = Color(0xFF222222) // Inner elements
val ElectricCyan = Color(0xFF00E5FF) // Primary Accent
val SoftCyan = Color(0xFF81ECFF) // Secondary Accent

val Border = Color(0xFF2A2A2A)

val Accent    = Color(0xFF00E5FF)
val AccentSoft= Color(0xFF81ECFF)
val Warning   = Color(0xFFFFAB40)
val Critical  = Color(0xFFFF5252)

// --- LANDING THEME COLORS ---
val LandingBackground = Color(0xFF0A0E14)
val LandingPrimary = Color(0xFF81ECFF)
val LandingSurface = Color(0xFF151A21)
val LandingSurfaceVariant = Color(0xFF20262F)
val LandingError = Color(0xFFFF716C)
val LandingTertiary = Color(0xFF8EFF71)
val LandingOutline = Color(0xFF44484F)