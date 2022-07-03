package com.sertac.smartclock.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primaryDark,
//    primaryVariant = Purple700,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    onPrimary = onPrimaryDark

)

private val LightColorPalette = lightColors(
    primary = primaryLight,
//    primaryVariant = Purple700,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    onPrimary = onPrimaryLight

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SmartClockTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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