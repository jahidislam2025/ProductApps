package com.qsoft.feed_data.mapper

import com.qsoft.database.model.ProductEntity
import com.qsoft.feed_domain.model.ProductModel
import com.qsoft.network.dto.Products

fun Products.toResponse(): ProductModel {
    return ProductModel(
        id = id ?: 0,
        title = title ?: "",
        description = description ?: "",
        image = if (images.isNotEmpty()) images[0] else "",
        brand = brand,
        price = price ?: 0.0,
        discountPercentage = discountPercentage ?: 0.0,
        category = category ?: "",
        rating = rating ?: 0.0,
        stock = stock ?: 0
    )
}

fun ProductModel.toEntity(): ProductEntity {
    return ProductEntity(
        productId = id,
        title = title,
        description = description,
        image = image,
        brand = brand,
        price = price,
        discountPercentage = discountPercentage,
        category = category,
        rating = rating,
        stock = stock,
        isFavorite = isFavorite
    )
}

fun ProductEntity.toResponse(): ProductModel {
    return ProductModel(
        id = productId,
        title = title,
        description = description,
        image = image,
        isFavorite = isFavorite,
        brand = brand,
        price = price,
        discountPercentage = discountPercentage,
        category = category,
        rating = rating,
        stock = stock
    )
}