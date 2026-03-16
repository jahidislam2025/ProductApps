package com.qsoft.feed_presentation.favorite

import com.qsoft.feed_domain.model.ProductModel

data class FavoriteState (
    val productsList: List<ProductModel> = emptyList()
)