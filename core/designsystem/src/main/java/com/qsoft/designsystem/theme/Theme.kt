package com.qsoft.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.qsoft.designsystem.ssp

private val DarkColorScheme = darkColorScheme(
    primary = ColorPrimaryDark,
    surfaceTint = ColorPrimaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPrimaryDark,
    surfaceTint = ColorPrimaryDark
)

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

@Composable
fun AssessmentTaskTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


val darkButtonTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.Bold,
    lineHeight = 23.ssp(),
    letterSpacing = .5.sp,
    textAlign = TextAlign.Left,
)

val heading1TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 28.ssp(),
    fontWeight = FontWeight.W700,
    textAlign = TextAlign.Center,
    lineHeight = 33.ssp(),
)

//styleName: Body light;
val bodyLightTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W300,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)

//styleName: Body light;
val bodyLMediumTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 18.ssp(),
    fontWeight = FontWeight.W500,
    //lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

//styleName: Body light;
val bodyLBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 18.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Center
)

val heading2TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 32.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 33.ssp(),
)

val preTitleTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 20.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 36.ssp(),
)
val boldBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 28.ssp(),
)


val normalBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.Normal,
    lineHeight = 28.ssp(),
)


val smallBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    lineHeight = 24.ssp(),
    fontWeight = FontWeight.W300
)

//styleName: Body regular;
val bodyRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W300,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Center
)
val bodyRegularSpanStyle = SpanStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W300
)

val subHeading1TextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 21.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Center
)
val subHeading1SpanStyle = SpanStyle(
    fontFamily = fontRoboto,
    fontSize = 21.ssp(),
    fontWeight = FontWeight.W700
)

val bodyXXSRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 12.ssp(),
    fontWeight = FontWeight.W300,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val bodyXXSBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 12.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 21.ssp(),
    textAlign = TextAlign.Left
)

val bodyXSBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 23.ssp(),
    textAlign = TextAlign.Left
)

val bodyBoldTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)


val bodyXSRegularTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    fontWeight = FontWeight.W300,
    lineHeight = 23.ssp(),
    textAlign = TextAlign.Left
)

val bodyXSMediumTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 14.ssp(),
    fontWeight = FontWeight.W500,
    lineHeight = 23.ssp(),
    textAlign = TextAlign.Left
)

val bodyMediumTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W500,
    lineHeight = 25.ssp(),
    textAlign = TextAlign.Left
)


val bodySmallTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 15.ssp(),
    fontWeight = FontWeight.W400,
    lineHeight = 24.ssp(),
    textAlign = TextAlign.Start
)


val subHeadingFormTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W500,
    lineHeight = 19.ssp(),
    textAlign = TextAlign.Left
)


val largeBodyTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W700,
    lineHeight = 28.ssp(),
)

val smallButtonOrLinkTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W500,
    lineHeight = 28.ssp(),
)

val labelTextStyle = TextStyle(
    fontFamily = fontRoboto,
    fontSize = 16.ssp(),
    fontWeight = FontWeight.W500,
    lineHeight = 28.ssp(),
    letterSpacing = 0.1.sp
)