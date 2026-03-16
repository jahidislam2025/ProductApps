package com.qsoft.designsystem.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.designsystem.r
import com.qsoft.designsystem.rippleClickable
import com.qsoft.designsystem.ssp
import com.qsoft.designsystem.theme.bodyXSBoldTextStyle
import com.qsoft.designsystem.theme.bodyXXSBoldTextStyle
import com.qsoft.common.R as CommonR
import com.qsoft.designsystem.R as DesignSystemR

@Composable
fun CustomNavigationItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes icon: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    isFavorite: Boolean = true,
    favoriteCount: Int = 0
) {
    val selectedColor =
        if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant.copy(
            alpha = 0.7f
        )


    Box(modifier = Modifier.clickable {
        onClick()
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.r())
        ) {


            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(if (isSelected) 18.r() else 16.r()),
                colorFilter = ColorFilter.tint(color = selectedColor)
            )




            Spacer(modifier = Modifier.height(4.r()))

            Text(
                text = stringResource(title),
                style = bodyXSBoldTextStyle.copy(
                    color = selectedColor
                )
            )

            Spacer(modifier = Modifier.height(4.r()))

        }

        if (isFavorite) {
            Text(
                text = "$favoriteCount",
                style = bodyXXSBoldTextStyle.copy(textAlign = TextAlign.Center,color = Color.White, fontSize = 10.ssp()),
                modifier = Modifier
                    .background(color = Color.Red, shape = CircleShape)
                    .size(16.r())
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCustomNavigationItem() {
    Column {
        CustomNavigationItem(
            title = CommonR.string.feed,
            icon = DesignSystemR.drawable.ic_feed,
            isSelected = true,
            onClick = {}
        )
    }
}