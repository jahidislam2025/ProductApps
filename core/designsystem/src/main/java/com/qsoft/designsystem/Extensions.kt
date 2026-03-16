package com.qsoft.designsystem

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.text.isLowerCase
import kotlin.text.replaceFirstChar
import kotlin.text.titlecase


fun Modifier.rippleClickable(
    onClick: () -> Unit
): Modifier = composed {
    this.clickable(
        onClick = onClick,
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple()
    )
}

fun String.capitalizeFirstChar(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyMMddHHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}