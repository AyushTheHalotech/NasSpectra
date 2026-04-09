package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.navigation

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Main : Screen("main")
    object Splash : Screen("splash")
}