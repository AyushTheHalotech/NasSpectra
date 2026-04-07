package com.thehalotech.nasspectra.feature_dashboard.presentation.ui.appscreen

import android.icu.lang.UCharacter.toUpperCase
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import com.thehalotech.nasspectra.R
import com.thehalotech.nasspectra.core.ui.theme.Accent
import com.thehalotech.nasspectra.core.ui.theme.TextPrimary
import com.thehalotech.nasspectra.core.ui.theme.TextSecondary
import com.thehalotech.nasspectra.core.ui.theme.Warning
import com.thehalotech.nasspectra.feature_dashboard.domain.model.AppInfo
import com.thehalotech.nasspectra.feature_dashboard.presentation.state.SectionState
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.ErrorCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.LoadingCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.components.StatCard
import com.thehalotech.nasspectra.feature_dashboard.presentation.ui.utils.toUiMessage
import com.thehalotech.nasspectra.feature_dashboard.presentation.viewmodel.AppViewModel


@Composable
fun AppScreen(viewModel: AppViewModel = hiltViewModel())  {

    val state by viewModel.state.collectAsState()
    AppSection(state.appList, { viewModel.loadApps() })
}

@Composable
fun AppSection(state: SectionState<List<AppInfo>>, onRetry: () -> Unit) {

    when {
        state.isLoading -> {
            LoadingCard(text = "Loading App List...")
        }

        state.error != null -> {
            ErrorCard(
                title = "Error",
                message = state.error.toUiMessage(),
                onRetry = onRetry
            )
        }

        state.data != null -> {
            AppList(state.data)
        }
    }
}

@Composable
fun AppList(apps: List<AppInfo>){
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)) {
        apps.forEach { app ->
            AppCard(app)
        }
    }
}

@Composable
fun AppCard(app: AppInfo) {
    val maintainersList = app.maintainers.joinToString { it.name }
    val latestVersionText = buildAnnotatedString {
        if (app.upgradeAvailable) {
            withStyle(style = SpanStyle(color = Warning)) {
                append("⚠️ ")
            }
        }
        append(app.latestVersion)
    }
    StatCard {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(app.icon)
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true) // Smooth transition animation
                        .build(),
                    placeholder = painterResource(R.drawable.ic_broken_image),
                    error = painterResource(R.drawable.ic_broken_image),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(32.dp)
                    .clip(RoundedCornerShape(6.dp)) // Shape transformation
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = app.name, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 18.sp)
                Text(text = app.version, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 18.sp, textAlign = TextAlign.End)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Id: ", fontWeight = FontWeight.SemiBold, color = Accent, fontSize = 14.sp)
                    Text(text = "Status: ", fontWeight = FontWeight.SemiBold, color = Accent, fontSize = 14.sp)
                    Text(text = "Latest Version: ", fontWeight = FontWeight.SemiBold, color = Accent, fontSize = 14.sp)
                    Text(text = "Train: ", fontWeight = FontWeight.SemiBold, color = Accent, fontSize = 14.sp)
                    Text(text = "Maintainers: ", fontWeight = FontWeight.SemiBold, color = Accent, fontSize = 14.sp)
                }

                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = app.id, fontWeight = FontWeight.Medium, color = TextSecondary, fontSize = 12.sp)
                    Text(text = app.state, fontWeight = FontWeight.Medium, color = TextSecondary, fontSize = 12.sp)
                    Text(text = latestVersionText, fontWeight = FontWeight.Medium, color = TextSecondary, fontSize = 12.sp)
                    Text(text = app.train, fontWeight = FontWeight.Medium, color = TextSecondary, fontSize = 12.sp)
                    Text(text = maintainersList, fontWeight = FontWeight.Medium, color = TextSecondary, fontSize = 12.sp)
                }

            }
            Spacer(modifier = Modifier.width(8.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp), color = Accent)
            Spacer(modifier = Modifier.width(8.dp))
            when(app.description) {
                "" -> Text(text = "No Description", color = TextSecondary, fontSize = 12.sp)
                else -> Text(text = app.description, color = TextSecondary, fontSize = 12.sp, fontStyle = FontStyle.Italic)
            }

        }

    }
}