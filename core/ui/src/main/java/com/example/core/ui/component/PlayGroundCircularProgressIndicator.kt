package com.example.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.designSystem.core.theme.PlayGround

@Composable
fun PlayGroundCircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .size(30.dp)
            .padding(vertical = PlayGround.spacing.one),
        strokeWidth = 1.dp,
        strokeCap = StrokeCap.Round,
    )
}
