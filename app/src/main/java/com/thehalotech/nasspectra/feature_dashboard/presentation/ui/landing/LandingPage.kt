package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.thehalotech.nasspectra.core.ui.theme.LandingBackground
import com.thehalotech.nasspectra.core.ui.theme.LandingPrimary
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LandingBackGroundEffects
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LandingHeader
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LandingTerminalCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.navigation.Screen
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.LandingViewModel

@Composable
fun LandingTerminal(
    navController: NavController,
    viewModel: LandingViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LandingBackground)
    ) {
        LandingBackGroundEffects()

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            LandingHeader()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                // Background Decorative Text (Aesthetic only)
                Text(
                    text = "> initializing handshake...\n> crypto_engine: enabled\n> session: 0x8F22A",
                    color = LandingPrimary,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 40.dp, top = 60.dp)
                        .alpha(0.15f)
                )

                LandingTerminalCard(
                    state = state,
                    onIpChange = viewModel::onIpChange,
                    onApiKeyChange = viewModel::onApiKeyChange,
                    onTestConnection = viewModel::onTestConnection,
                    onNext = {
                        viewModel.onNext {
                            navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Landing.route) { inclusive = true }
                            }
                        }
                    }
                )
            }

            // Footer
            Text(
                text = "NAS Command and Control Centre",
                color = Color.White.copy(alpha = 0.2f),
                fontSize = 10.sp,
                letterSpacing = 4.sp,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
