package com.qsoft.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.designsystem.r
import com.qsoft.designsystem.theme.BACKGROUND_COLOR
import com.qsoft.designsystem.theme.bodyRegularTextStyle

@Composable
fun AuthTopBar(onLanguageClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BACKGROUND_COLOR),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(4.r()),
            modifier = Modifier.shadow(
                elevation = 5.r(),
                spotColor = Color.LightGray,
                shape = RoundedCornerShape(4.r())
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.r(), horizontal = 10.r())
            ) {
                Text(
                    text = "English (UK)", style = bodyRegularTextStyle
                )

                Spacer(modifier = Modifier.width(7.r()))

            }
        }

    }
}


@Composable
@Preview
fun PreviewAuthTopBar() {
    AuthTopBar(
        onLanguageClick = {}
    )
}