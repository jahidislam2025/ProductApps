package com.qsoft.designsystem.components

import android.annotation.SuppressLint
import android.util.Patterns
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.designsystem.r
import com.qsoft.designsystem.theme.ColorError
import com.qsoft.designsystem.theme.bodyRegularTextStyle
import com.qsoft.designsystem.theme.grayScale
import com.qsoft.designsystem.theme.primaryBlue
import kotlin.let
import kotlin.text.isEmpty
import com.qsoft.designsystem.R as DesignSystemR

@Composable
fun CommonTextField(
    readOnly: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    placeholder: String,
    isTouched: Boolean,
    isValid: Boolean,
    onTouched: () -> Unit,
    leadingIcon: Painter? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = bodyRegularTextStyle,
    cursorColor: Color? = null,
    shape: RoundedCornerShape = RoundedCornerShape(16.r())
) {
    val scheme = MaterialTheme.colorScheme

    val effectiveTextStyle = textStyle.copy(
        color = scheme.onSurface,
        textAlign = TextAlign.Start
    )
    val effectiveCursor = cursorColor ?: scheme.primary

    val isError = isTouched && !isValid
    val borderColor = when {
        isError -> scheme.error
        isTouched -> scheme.primary
        else -> scheme.outline.copy(alpha = 0.35f)
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(width = 1.r(), shape = shape, color = borderColor)
            .background(color = scheme.surfaceColorAtElevation(1.r()), shape)
            .padding(horizontal = 10.r())
            .onFocusEvent { event -> if (event.isFocused) onTouched() }
            .pointerInteropFilter {
                if (it.action == MotionEvent.ACTION_DOWN) onTouched()
                false
            }
    ) {
        leadingIcon?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.size(16.r()),
            )
        }

        Spacer(modifier = Modifier.width(8.r()))

        BasicTextField(
            readOnly = readOnly,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = effectiveTextStyle,
            keyboardOptions = keyboardOptions,
            cursorBrush = SolidColor(effectiveCursor),
            modifier = modifier
                .weight(1f)
                .padding(vertical = 15.r()),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = effectiveTextStyle.copy(
                            color = scheme.onSurfaceVariant.copy(alpha = 0.7f),
                            textAlign = TextAlign.Start
                        )
                    )
                }
                innerTextField()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmailTextField() {
    var email by remember { mutableStateOf("") }
    var isTouched by remember { mutableStateOf(false) }
    val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    CommonTextField(
        value = email,
        onValueChange = { email = it },
        isTouched = isTouched,
        isValid = isValid,
        onTouched = { isTouched = true },
        placeholder = "Enter your email",
        leadingIcon = painterResource(id = DesignSystemR.drawable.ic_mail),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

