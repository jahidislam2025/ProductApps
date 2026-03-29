package com.qsoft.feed_presentation.feed

import com.qsoft.feed_domain.model.ProductModel

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: ProductModel? = null,
    val error: String = ""
)