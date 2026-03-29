package com.qsoft.feed_presentation.feed

import com.qsoft.feed_domain.model.ProductModel

sealed class FeedEvent {
    data class OnFavoriteClickEvent(val productId: Int, val isFavorite: Boolean) : FeedEvent()
    data class OnSearchEvent(val searchKey: String) : FeedEvent()
    data class OnProductClickEvent(val product: ProductModel) : FeedEvent()
    data object OnBackFromDetailEvent : FeedEvent()
}