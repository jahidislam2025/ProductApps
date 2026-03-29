package com.qsoft.feed_domain.model

data class ProductModel(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val isFavorite: Boolean = false,
    val brand: String? = null,
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val category: String = "",
    val rating: Double = 0.0,
    val stock: Int = 0
)