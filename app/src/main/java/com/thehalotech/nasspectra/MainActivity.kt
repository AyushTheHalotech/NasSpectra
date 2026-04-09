package com.thehalotech.nasspectra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.main.MainScreen
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.navigation.AppNavHost
import com.thehalotech.nasspectra.ui.theme.NasSpectraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NasSpectraTheme {
                AppNavHost()
            }
        }
    }
}