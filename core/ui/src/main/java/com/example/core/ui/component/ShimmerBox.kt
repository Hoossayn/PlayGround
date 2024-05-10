package com.example.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    shimmer: Shimmer,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier.background(Color.Transparent)) {
        content()
        if (isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .shimmer(shimmer)
                    .background(Color.Black.copy(alpha = 0.8f)),
            )
        }
    }
}
