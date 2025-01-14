package com.example.passify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    onBackground = DarkOnBackground,
    primary = DarkAccent,
    error = DarkError,
    tertiary = DarkWarning,
    secondary = DarkSuccess,
    surface = DarkInactiveBackground,
    onSurface = DarkOnBackgroundSecondary,
    onSurfaceVariant = DarkOnBackground
)

// Светлая цветовая схема
private val LightColorScheme = lightColorScheme(
    background = LightBackground,
    onBackground = LightOnBackground,
    primary = LightAccent,
    error = LightError,
    tertiary = LightWarning,
    secondary = LightSuccess,
    surface = LightInactiveBackground,
    onSurface = LightShadow,
    onSurfaceVariant = LightShadowOnBackground
)

@Composable
fun YourAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}