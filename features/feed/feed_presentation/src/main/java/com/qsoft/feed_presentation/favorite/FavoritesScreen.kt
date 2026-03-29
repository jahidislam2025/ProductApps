package com.qsoft.feed_presentation.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.designsystem.r
import com.qsoft.designsystem.theme.bodyBoldTextStyle
import com.qsoft.feed_presentation.feed.ProductRow
import com.qsoft.common.R as CommonR

@Composable
fun FavoritesScreen(state: FavoriteState, onEvent: (FavoriteEvent) -> Unit) {
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.r())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        if (state.productsList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(CommonR.string.no_items_found),
                    style = bodyBoldTextStyle,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(10.r())
            ) {
                itemsIndexed(state.productsList) { _, item ->
                    ProductRow(
                        item = item,
                        onItemClick = {
                            // item click logic later
                        },
                        onFavoriteClick = {
                            onEvent(
                                FavoriteEvent.OnFavoriteClickEvent(
                                    productId = item.id,
                                    isFavorite = !item.isFavorite
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewFavoritesScreen() {
    FavoritesScreen(
        state = FavoriteState(),
        onEvent = {}
    )
}