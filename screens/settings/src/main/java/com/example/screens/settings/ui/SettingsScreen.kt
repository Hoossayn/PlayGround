package com.example.screens.settings.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.core.common.annotation.ExcludeFromGeneratedCoverageReport
import com.example.core.model.ThemeConfig
import com.example.core.ui.component.Background
import com.example.core.ui.widget.MovieRating
import com.example.designSystem.core.component.PlayGroundButton
import com.example.designSystem.core.theme.FourVerticalSpacer
import com.example.designSystem.core.theme.OneAndHalfHorizontalSpacer
import com.example.designSystem.core.theme.OneHorizontalSpacer
import com.example.designSystem.core.theme.OneVerticalSpacer
import com.example.designSystem.core.theme.PlayGround
import com.example.designSystem.core.theme.TwoAndHalfHorizontalSpacer
import com.example.designSystem.core.theme.TwoHorizontalSpacer
import com.example.designSystem.core.theme.TwoVerticalSpacer
import com.example.designSystem.core.theme.supportsDynamicTheming
import com.example.screens.settings.R
import com.example.screens.settings.viewModel.SettingsUiState
import com.example.screens.settings.viewModel.SettingsViewModel

const val MORE_TITLE_TEST_TAG = "more_title"

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    val onLogoutClick: () -> Unit = {viewModel.onLogout()}
    val onDynamicColorPreferenceChanged: (useDynamicColor: Boolean) -> Unit = {
        viewModel.onDynamicColourPreferenceChanged(it)
    }
    val onDarkThemeConfigChanged: (themeConfig: ThemeConfig) -> Unit = {
        viewModel.onThemeConfig(it)
    }
    SettingsScreen(
        settingsUiState = settingsUiState,
        onChangeDynamicColorPreference = onDynamicColorPreferenceChanged,
        onChangeDarkThemeConfig = onDarkThemeConfigChanged,
        onLoginClick = onLoginClick,
        onLogoutClick = onLogoutClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExcludeFromGeneratedCoverageReport
fun SettingsScreen(
    settingsUiState: SettingsUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (themeConfig: ThemeConfig) -> Unit,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    var showThemeSettingsDialog by remember { mutableStateOf(false) }

    Background {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        style = PlayGround.typography.titleMedium,
                        color = PlayGround.color.onPrimary
                    )
                },
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                )
            )
            TwoAndHalfHorizontalSpacer()
            Box {
                when (settingsUiState) {
                    is SettingsUiState.Success -> {
                        val preference = settingsUiState.preference
                        val configuration = LocalConfiguration.current

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item { UserDetails(profilePath = "") }
                            item { MovieAndTVScore() }
                            item { UserAccountStat() }
                            item {
                                UserAccountExtraSettings(
                                    isLoggedIn = preference.isLoggedIn,
                                    onLoginClick = onLoginClick,
                                    onLogoutClick = onLogoutClick,
                                    onChangeTheme = { showThemeSettingsDialog = true },
                                )
                            }
                        }

                        if (showThemeSettingsDialog) {
                            AlertDialog(
                                onDismissRequest = { showThemeSettingsDialog = false },
                                properties = DialogProperties(usePlatformDefaultWidth = false),
                                modifier = Modifier.widthIn(
                                    max = configuration.screenHeightDp.dp - 80.dp
                                ),
                                title = {
                                    Text(
                                        text = stringResource(R.string.theme_settings),
                                        style = MaterialTheme.typography.titleLarge,
                                    )
                                },
                                text = {
                                    HorizontalDivider()
                                    Column(Modifier.verticalScroll(rememberScrollState())) {
                                        SettingsPanel(
                                            themeConfig = preference.themeConfig,
                                            supportDynamicColor = supportDynamicColor,
                                            useDynamicColor = preference.isDynamicColor,
                                            onChangeDynamicColorPreference =
                                            onChangeDynamicColorPreference,
                                            onChangeDarkThemeConfig = onChangeDarkThemeConfig,
                                        )
                                        HorizontalDivider(Modifier.padding(top = 8.dp))
                                    }
                                },
                                confirmButton = {
                                    Text(
                                        text = stringResource(R.string.dismiss_dialog_button_text),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .clickable { showThemeSettingsDialog = false },
                                    )
                                }
                            )
                        }
                    }
                    SettingsUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(30.dp)
                                .padding(vertical = PlayGround.spacing.one),
                            strokeWidth = 1.dp,
                            strokeCap = StrokeCap.Round,
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun ColumnScope.SettingsPanel(
    themeConfig: ThemeConfig,
    useDynamicColor: Boolean,
    supportDynamicColor: Boolean,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (themeConfig: ThemeConfig) -> Unit,
) {
    AnimatedVisibility(visible = supportDynamicColor) {
        Column {
            SettingsDialogSectionTitle(text = stringResource(R.string.dynamic_color_preference))
            Column(Modifier.selectableGroup()) {
                SettingsDialogThemeChooserRow(
                    text = stringResource(R.string.dynamic_color_yes),
                    selected = useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(true) },
                )
                SettingsDialogThemeChooserRow(
                    text = stringResource(R.string.dynamic_color_no),
                    selected = !useDynamicColor,
                    onClick = { onChangeDynamicColorPreference(false) },
                )
            }
        }
    }
    SettingsDialogSectionTitle(text = stringResource(R.string.theme_preference))
    Column(Modifier.selectableGroup()) {
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.theme_config_system_default),
            selected = themeConfig == ThemeConfig.FOLLOW_SYSTEM,
            onClick = { onChangeDarkThemeConfig(ThemeConfig.FOLLOW_SYSTEM) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.theme_config_light),
            selected = themeConfig == ThemeConfig.LIGHT,
            onClick = { onChangeDarkThemeConfig(ThemeConfig.LIGHT) },
        )
        SettingsDialogThemeChooserRow(
            text = stringResource(R.string.theme_config_dark),
            selected = themeConfig == ThemeConfig.DARK,
            onClick = { onChangeDarkThemeConfig(ThemeConfig.DARK) },
        )
    }
}

@Composable
private fun UserAccountExtraSettings(
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onChangeTheme: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PlayGround.spacing.two),
    ) {
        SettingsDialogSectionTitle(text = stringResource(R.string.settings))
        Divider(color = PlayGround.color.onPrimary.copy(alpha = 0.5F))

        TwoVerticalSpacer()
        ChangeThemeButton(onClick = onChangeTheme)

        FourVerticalSpacer()
        Crossfade(
            targetState = isLoggedIn,
            label = "preference_isLoggedIn",
        ) {
            when (it) {
                true -> PlayGroundButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLogoutClick,
                ) {
                    Text(text = stringResource(R.string.logout))
                }

                false -> PlayGroundButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLoginClick,
                ) {
                    Text(text = stringResource(R.string.login))
                }
            }
        }
    }
}

@Composable
private fun ChangeThemeButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.semantics(true) { },
        onClick = onClick,
        shape = PlayGround.shape.small,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PlayGround.spacing.one, vertical = PlayGround.spacing.oneAndHalf),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.ColorLens,
                contentDescription = stringResource(R.string.change_theme),
            )
            OneHorizontalSpacer()
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(R.string.theme_settings),
                    style = PlayGround.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = stringResource(R.string.choose_from_light_or_dark_theme),
                    style = PlayGround.typography.labelMedium,
                )
            }
        }
    }
}

@Composable
private fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp)
            .clip(PlayGround.shape.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
private fun MovieAndTVScore() {
    TwoVerticalSpacer()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PlayGround.spacing.two),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            MovieRating(
                modifier = Modifier.size(57.dp),
                rating = 0.45F,
            )
            OneAndHalfHorizontalSpacer()
            Text(
                text = "Average \nMovie Score",
                maxLines = 2,
                minLines = 2,
            )
        }
        TwoHorizontalSpacer()
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            MovieRating(
                modifier = Modifier.size(57.dp),
                rating = 0.45F,
            )
            OneAndHalfHorizontalSpacer()
            Text(
                text = "Average \nTV Score",
                maxLines = 2,
                minLines = 2,
            )
        }
    }
}

@Composable
private fun UserAccountStat() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PlayGround.spacing.two),
    ) {
        OneVerticalSpacer()
        SettingsDialogSectionTitle(text = "Stats")
        Divider(color = PlayGround.color.onPrimary.copy(alpha = 0.2F))
        TwoVerticalSpacer()
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1F),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(R.string.total_edits),
                    style = PlayGround.typography.labelMedium,
                )
                Text(
                    text = "0",
                    style = PlayGround.typography.displayLarge,
                    color = PlayGround.color.secondaryContainer,
                )
            }
            TwoHorizontalSpacer()
            Column(
                modifier = Modifier.weight(1F),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(R.string.total_ratings),
                    style = PlayGround.typography.labelMedium,
                )
                Text(
                    text = "0",
                    style = PlayGround.typography.displayLarge,
                    color = PlayGround.color.secondaryContainer,
                )
            }
        }
    }
}

@Composable
private fun UserDetails(profilePath: String) {
    val isLocalInspection = LocalInspectionMode.current
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(profilePath)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.FillWidth,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PlayGround.spacing.two),
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .aspectRatio(1F)
                .align(Alignment.CenterHorizontally),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        color = PlayGround.color.tertiaryContainer.copy(alpha = 0.5F),
                        shape = CircleShape,
                    ),
                painter = if (isError.not() && !isLocalInspection) {
                    imageLoader
                } else {
                    painterResource(com.example.core.ui.R.drawable.ic_avatar)
                },
                contentScale = ContentScale.Inside,
                contentDescription = null,
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                        .padding(PlayGround.spacing.twoAndaHalf),
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 1.dp,
                )
            }
        }
        OneVerticalSpacer()
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Hakeem",
            style = PlayGround.typography.displaySmall,
            color = contentColorFor(backgroundColor = PlayGround.color.inverseOnSurface),
            minLines = 1,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Member since November 2016",
            style = PlayGround.typography.labelSmall,
            color = contentColorFor(backgroundColor = PlayGround.color.inverseOnSurface),
            minLines = 1,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
        TwoVerticalSpacer()
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "A software engineer! Member since November 2016",
            style = PlayGround.typography.bodyMedium,
            color = contentColorFor(backgroundColor = PlayGround.color.inverseOnSurface),
            minLines = 1,
            maxLines = 1,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = PlayGround.spacing.two, bottom = PlayGround.spacing.half),
    )
}

