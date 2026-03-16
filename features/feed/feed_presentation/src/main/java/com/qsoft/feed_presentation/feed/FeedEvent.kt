package com.qsoft.feed_presentation.feed

sealed class FeedEvent {
    data class OnFavoriteClickEvent(val productId: Int, val isFavorite: Boolean): FeedEvent()
    data class OnSearchEvent(val searchKey: String): FeedEvent()
}