package com.example.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.ui.R
import com.example.designSystem.core.annotation.ThemePreviews
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.PlayGroundTheme

@Composable
fun TrailerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(modifier = modifier.semantics(mergeDescendants = true) { }, onClick = onClick) {
        Row(horizontalArrangement = Arrangement.spacedBy(PlayGround.spacing.two)) {
            Icon(
                painterResource(id = R.drawable.ic_watch_trailer),
                contentDescription = stringResource(R.string.title_watch_trailer),
            )

            Text(text = stringResource(R.string.title_watch_trailer))
        }
    }
}

@Composable
@ThemePreviews
@ExcludeFromGeneratedCoverageReport
fun TrailerButtonPreview() {
    PlayGroundTheme(content = { TrailerButton() })
}
