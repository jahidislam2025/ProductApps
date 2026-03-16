package com.qsoft.feed_domain.model

data class ProductModel(
    val id: Int,
    val title: String,
    val description: String,
    var image: String,
    var isFavorite: Boolean = false
)
