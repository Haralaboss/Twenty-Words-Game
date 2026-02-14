package com.savvi.twentywords.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Charcoal,
    secondary = Bittersweet,
    tertiary = Sunglow,
)

private val LightColorScheme = lightColorScheme(
    primary = Charcoal,
    secondary = Bittersweet,
    tertiary = Sunglow,

     //Other default colors to override
    background = UranianBlue,
    surface = Color(0xFFFFFBFE),
    onPrimary = Sunglow,
    onSecondary = Sunglow,
    onTertiary = Sunglow,
    onBackground = Charcoal,
    onSurface = Charcoal,


)

@Composable
fun TwentyWordsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}