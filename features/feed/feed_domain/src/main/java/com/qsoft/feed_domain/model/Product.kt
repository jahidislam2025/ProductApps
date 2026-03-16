package com.qsoft.feed_domain.model

data class Product(
    val id: Int,
    val title: String,
    val category: String,
    val brand: String,
    val price: Double,
    val discountedPrice: Double,
    val image: String,
    val rating: Double
)
