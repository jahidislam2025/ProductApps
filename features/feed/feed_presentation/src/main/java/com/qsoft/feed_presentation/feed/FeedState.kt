package com.qsoft.feed_presentation.feed

import com.qsoft.feed_domain.model.ProductModel

data class FeedState(
    val isLoading: Boolean = false,
    val productsList: List<ProductModel> = emptyList(),
    val endReached: Boolean = false,
    val error: String = "",
    val searchKey: String = ""
)
