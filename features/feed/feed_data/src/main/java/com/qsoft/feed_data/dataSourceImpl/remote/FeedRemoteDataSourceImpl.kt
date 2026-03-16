package com.qsoft.feed_data.dataSourceImpl.remote

import com.qsoft.feed_data.dataSource.remote.FeedRemoteDataSource
import com.qsoft.network.PublicApiService
import com.qsoft.network.dto.ProductsDto

class FeedRemoteDataSourceImpl(val publicApiService: PublicApiService) :
    FeedRemoteDataSource {
    override suspend fun getProducts(limit: Int, skip: Int): ProductsDto {
        return publicApiService.getProducts(limit, skip)
    }
}