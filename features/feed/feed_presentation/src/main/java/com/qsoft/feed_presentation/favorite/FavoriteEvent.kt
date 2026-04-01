package com.qsoft.feed_presentation.favorite

import com.qsoft.feed_domain.model.ProductModel

sealed class FavoriteEvent {
    data class OnFavoriteClickEvent(
        val productId: Int,
        val isFavorite: Boolean
    ) : FavoriteEvent()

    data class OnProductClickEvent(
        val product: ProductModel
    ) : FavoriteEvent()
}