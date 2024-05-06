package com.example.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.design_system.core.annotation.ThemePreviews
import com.example.design_system.core.theme.PlayGround
import com.example.design_system.core.theme.PlayGroundTheme


@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
private fun BackgroundPreview() {
    PlayGroundTheme {
        Background {
            Text(text = "Background")
        }
    }
}

@Composable
fun Background(content: @Composable () -> Unit) {
    @Suppress("MagicNumber")
    val colorStops = listOf(
        PlayGround.color.primary,
        Color(0x00000000),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlayGround.color.surface),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorStops)),
        )
        content()
    }
}
