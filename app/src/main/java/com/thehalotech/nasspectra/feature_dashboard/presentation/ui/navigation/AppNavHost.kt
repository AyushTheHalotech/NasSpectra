package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.landing.LandingTerminal
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.main.MainScreen
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.splashscreen.SplashScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }) {
            SplashScreen(navController)

        }

        composable(Screen.Landing.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                ) + fadeIn()
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                ) + fadeOut()
            }) {
            LandingTerminal(navController)
        }

        composable(Screen.Main.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left
                ) + fadeIn()
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right
                ) + fadeOut()
            }) {
            MainScreen()
        }
    }
}