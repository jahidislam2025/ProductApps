package com.qsoft.feed_domain.repository

import com.qsoft.feed_domain.model.ProductModel
import com.qsoft.network.model.CommonErrorModel
import com.qsoft.network.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    suspend fun getProducts(limit: Int, skip: Int): ResultWrapper<List<ProductModel>, CommonErrorModel>
    suspend fun deleteProducts()
    fun getLocalProducts(searchKey: String): Flow<List<ProductModel>>
    fun getFavoriteProducts(): Flow<List<ProductModel>>
    fun getFavoriteProductsCount(): Flow<Int>
    suspend fun updateFavorite(productId: Int, isFavorite: Boolean)
}