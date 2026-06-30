package com.example.prakashjewellers.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Updated to lightColorScheme for a clean white background
val LightColorScheme = lightColorScheme(
    primary = GoldPrimary,
    secondary = GoldDark,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,    // Text/Icons on top of primary (Gold)
    onBackground = Color.Black, // Main text on the white background
    onSurface = Color.Black     // Text on cards/surfaces
)

// The GoldPrimary, GoldDark, etc., should be defined in your Color.kt