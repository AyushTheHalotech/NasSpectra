package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.thehalotech.nasspectra.core.ui.theme.LandingBackground
import com.thehalotech.nasspectra.core.ui.theme.LandingPrimary
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.navigation.Screen
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val destination by viewModel.destination.collectAsState()

    // Minimal UI (you can style this like your terminal theme 🔥)
    Box(
        modifier = Modifier.fillMaxSize().background(LandingBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text ="> initializing...\n> checking credentials...\n> establishing link...",
            fontFamily = FontFamily.Monospace,
            color = LandingPrimary,
            modifier = Modifier.alpha(0.6f)
        )
    }

    LaunchedEffect(destination) {
        destination?.let {
            navController.navigate(it) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }
}