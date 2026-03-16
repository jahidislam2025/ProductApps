package com.qsoft.designsystem.components

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.designsystem.r
import com.qsoft.designsystem.theme.BACKGROUND_COLOR
import com.qsoft.designsystem.theme.bodyBoldTextStyle
import com.qsoft.designsystem.theme.primaryBlue
import com.qsoft.common.R as CommonR

@Composable
fun AppActionButton(
    @DrawableRes icon: Int? = null,
    @StringRes text: Int,
    bgColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    textStyle: TextStyle = bodyBoldTextStyle,
    radius: Int = 16,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {

    Button(
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(radius),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    ) {
        Row(
            modifier = modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            icon?.let {
                Image(
                    contentDescription = null,
                    painter = painterResource(icon)
                )
                Spacer(modifier = Modifier.width(8.r()))
            }
            Text(
                text = stringResource(text),
                style = textStyle.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
@Preview
fun PreviewAppActionButton() {
    Column(
        modifier = Modifier
            .height(200.r())
            .background(color = BACKGROUND_COLOR)
    ) {
        Column(modifier = Modifier.padding(20.r())) {

            AppActionButton(
                text = CommonR.string.sign_in,
                bgColor = primaryBlue,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}