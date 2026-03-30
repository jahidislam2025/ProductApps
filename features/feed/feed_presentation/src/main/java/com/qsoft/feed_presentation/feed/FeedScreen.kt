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
//Step 1: Screen শুরু হলে
fun FeedScreen(
    state: FeedState, // ViewModel থেকে data আসে
    onEvent: (FeedEvent) -> Unit, // User action handle করে
    loadNextPage: () -> Unit // নতুন page load করে
) {
    //Step 2: Product Select হয়েছে কিনা check করে
    val selectedProduct = state.selectedProduct

    if (selectedProduct != null) {
        // Product click করলে → Detail Screen দেখায়
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
        return // FeedScreen এর বাকি code run হয় না
    }

    //Step 3: Scroll এর শেষে নতুন page load করে

    val listState = rememberLazyListState()

    listState.OnBottomReached(buffer = 3) {
        loadNextPage() // শেষের ৩টা item দেখালেই নতুন page load হয়
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.r())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        //Step 4: Search Bar
        CommonTextField(
            value = state.searchKey,
            onValueChange = {
                onEvent(FeedEvent.OnSearchEvent(it)) // টাইপ করলে search হয়
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
        //.Step 5: Product List দেখায়
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(10.r())
        ) {
            items(items = state.productsList, key = { item -> item.id }) { item ->
                ProductRow(// প্রতিটা product এই card এ দেখায়
                    item = item,
                    onItemClick = {
                        onEvent(FeedEvent.OnProductClickEvent(item)) // click → detail
                    },
                    onFavoriteClick = {
                        onEvent( // heart click → favorite
                            FeedEvent.OnFavoriteClickEvent(
                                productId = item.id,
                                isFavorite = !item.isFavorite
                            )
                        )
                    }
                )
            }

            // List এর শেষে Loading বা Error দেখায়

            item {
                when {
                    state.isLoading -> LoadingRow() // loading spinner
                    // error message
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
                        text = item.title, // Product নাম
                        style = bodyBoldTextStyle.copy(textAlign = TextAlign.Start)
                    )

                    Spacer(modifier = Modifier.height(10.r()))

                    Text(
                        text = item.description,// Description
                        style = smallBodyTextStyle.copy(textAlign = TextAlign.Start)
                    )
                }

                if (item.image.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.r()))
                    DrawableCircleImageUrl(
                        imageUrl = item.image,// Product ছবি
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
                    painter = painterResource(DesignSystemR.drawable.ic_heart), // ❤️ Favorite button
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
        state = FeedState(),  // ViewModel থেকে UI তে data আসে
                              // যেমন: productsList, searchKey, isLoading, error
        onEvent = {}, // User কিছু করলে ViewModel কে জানায়
                       // যেমন: search, click, favorite, back
        loadNextPage = {}  // Scroll শেষে নতুন products load করে
                           // যেমন: page 1 → page 2 → page 3

                //**সম্পূর্ণ Flow:**
    //Screen load
    //↓
   // selectedProduct null? → না → ProductDetailScreen দেখাও
    //↓ হ্যাঁ
    //Search Bar দেখাও
   // ↓
    //LazyColumn এ product list দেখাও
   // ↓
    //Scroll শেষে → নতুন page load
   // ↓
    //Product click → OnProductClickEvent → selectedProduct set
    //↓
    //Heart click → OnFavoriteClickEvent → DB update → UI update
    )
}