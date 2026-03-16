package com.qsoft.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.qsoft.designsystem.R as DesignSystemR

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val fontRoboto = FontFamily(
    Font(DesignSystemR.font.roboto_black),
    Font(DesignSystemR.font.roboto_regular, FontWeight.Normal),
    Font(DesignSystemR.font.roboto_bold, FontWeight.Bold),
    Font(DesignSystemR.font.roboto_light, FontWeight.Light),
    Font(DesignSystemR.font.roboto_medium, FontWeight.Medium),
    Font(DesignSystemR.font.roboto_thin, FontWeight.Thin),
)