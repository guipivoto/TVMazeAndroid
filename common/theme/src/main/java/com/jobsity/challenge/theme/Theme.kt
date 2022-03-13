package com.jobsity.challenge.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue_500,
    primaryVariant = Blue_700,
    secondary = Blue_100,
    secondaryVariant = Blue_100
)

private val LightColorPalette = lightColors(
    primary = Blue_500,
    primaryVariant = Blue_700,
    secondary = Blue_100,
    secondaryVariant = Blue_100
)

@Composable
fun TVTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}