package com.qsoft.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.designsystem.r
import com.qsoft.designsystem.theme.subHeading1TextStyle
import com.qsoft.common.R as CommonR

@Composable
fun TopAppBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(10.r())) {

            Text(text = stringResource(CommonR.string.app_name), style = subHeading1TextStyle)
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.r())
                .shadow(elevation = 2.r(), spotColor = MaterialTheme.colorScheme.onSurface)
        )
    }
}

@Composable
@Preview
fun PreviewTopAppBar() {
    TopAppBar()
}