package com.example.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.design_system.core.annotation.ThemePreviews
import com.example.design_system.core.theme.PlayGround
import com.example.design_system.core.theme.PlayGroundTheme


@ThemePreviews
@Composable
@ExcludeFromGeneratedCoverageReport
fun GenreChipPreview() {
    PlayGroundTheme {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(PlayGround.color.surface),
        ) {
            GenreChip(
                modifier = Modifier.align(Alignment.Center),
                text = "Drama",
            )
        }
    }
}

@Composable
fun GenreChip(
    modifier: Modifier = Modifier,
    text: String,
) {
    Surface(
        modifier = modifier
            .wrapContentSize()
            .semantics(mergeDescendants = true) { },
        shape = PlayGround.shape.pill,
        color = PlayGround.color.tertiaryContainer,
        content = {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = PlayGround.spacing.half,
                        horizontal = PlayGround.spacing.oneAndHalf,
                    ),
                text = text,
                color = contentColorFor(PlayGround.color.tertiaryContainer),
            )
        },
    )
}
