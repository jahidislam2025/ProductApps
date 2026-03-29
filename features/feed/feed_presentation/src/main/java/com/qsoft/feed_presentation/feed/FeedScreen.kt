package com.qsoft.feed_presentation.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.qsoft.designsystem.components.CommonTextField
import com.qsoft.designsystem.r
import com.qsoft.designsystem.theme.bodyBoldTextStyle
import com.qsoft.designsystem.theme.smallBodyTextStyle
import com.qsoft.designsystem.utils.DrawableCircleImageUrl
import com.qsoft.designsystem.utils.ErrorRow
import com.qsoft.designsystem.utils.LoadingRow
import com.qsoft.designsystem.utils.OnBottomReached
import com.qsoft.feed_domain.model.ProductModel
import com.qsoft.designsystem.R as DesignSystemR
import com.qsoft.common.R as CommonR

@Composable
fun FeedScreen(
    state: FeedState,
    onEvent: (FeedEvent) -> Unit,
    loadNextPage: () -> Unit
) {
    val selectedProduct = state.selectedProduct

    if (selectedProduct != null) {
        ProductDetailScreen(
            state = ProductDetailState(
                isLoading = false,
                product = selectedProduct,
                error = ""
            ),
            onBackClick = {
                onEvent(FeedEvent.OnBackFromDetailEvent)
            },
            onFavoriteClick = { productId, isFavorite ->
                onEvent(
                    FeedEvent.OnFavoriteClickEvent(
                        productId = productId,
                        isFavorite = isFavorite
                    )
                )
            }
        )
        return
    }

    val listState = rememberLazyListState()

    listState.OnBottomReached(buffer = 3) {
        loadNextPage()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.r())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CommonTextField(
            value = state.searchKey,
            onValueChange = {
                onEvent(FeedEvent.OnSearchEvent(it))
            },
            onTouched = {},
            placeholder = stringResource(CommonR.string.search),
            isTouched = false,
            isValid = false,
            modifier = Modifier
                .padding(vertical = 10.r())
                .testTag("search_field"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(10.r())
        ) {
            items(items = state.productsList, key = { item -> item.id }) { item ->
                ProductRow(
                    item = item,
                    onItemClick = {
                        onEvent(FeedEvent.OnProductClickEvent(item))
                    },
                    onFavoriteClick = {
                        onEvent(
                            FeedEvent.OnFavoriteClickEvent(
                                productId = item.id,
                                isFavorite = !item.isFavorite
                            )
                        )
                    }
                )
            }

            item {
                when {
                    state.isLoading -> LoadingRow()
                    state.error.isNotBlank() -> ErrorRow(state.error) { loadNextPage() }
                }
            }
        }
    }
}

@Composable
fun ProductRow(
    item: ProductModel,
    onItemClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.r(), spotColor = MaterialTheme.colorScheme.surfaceDim)
            .clickable { onItemClick() }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.r(), horizontal = 5.r())
                ) {
                    Text(
                        text = item.title,
                        style = bodyBoldTextStyle.copy(textAlign = TextAlign.Start)
                    )

                    Spacer(modifier = Modifier.height(10.r()))

                    Text(
                        text = item.description,
                        style = smallBodyTextStyle.copy(textAlign = TextAlign.Start)
                    )
                }

                if (item.image.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.r()))
                    DrawableCircleImageUrl(
                        imageUrl = item.image,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.r())
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.r())
            ) {
                Image(
                    painter = painterResource(DesignSystemR.drawable.ic_heart),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = if (item.isFavorite) Color.Red else Color.LightGray
                    ),
                    modifier = Modifier
                        .size(24.r())
                        .clickable { onFavoriteClick() }
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewFeedScreen() {
    FeedScreen(
        state = FeedState(),
        onEvent = {},
        loadNextPage = {}
    )
}