package com.appdevcourse.bengaliappv2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    primaryVariant = Color(0xff607D8B),
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Color(0xff546E7A),
    primaryVariant = Color(0xff607D8B),
//    secondary = Color.White,
//    surface = Color.White,

//    primary = Purple500,
//    primaryVariant = Purple700,
    onPrimary = Color.White,

    secondary = Teal200,
    secondaryVariant = Teal700,
    background = Color.White,
    onSecondary = Color.Black

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
fun BengaliAppV2Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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