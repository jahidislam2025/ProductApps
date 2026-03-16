package com.qsoft.feed_data.dataSource.remote

import com.qsoft.network.dto.ProductsDto

interface FeedRemoteDataSource {
    suspend fun getProducts( limit: Int, skip: Int): ProductsDto
}