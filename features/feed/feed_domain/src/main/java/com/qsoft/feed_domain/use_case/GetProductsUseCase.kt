package com.qsoft.feed_domain.use_case

import com.qsoft.feed_domain.model.ProductModel
import com.qsoft.feed_domain.repository.FeedRepository
import com.qsoft.network.model.CommonErrorModel
import com.qsoft.network.utils.ResultWrapper

class GetProductsUseCase(private val feedRepository: FeedRepository) {
    suspend operator fun invoke(limit: Int, skip: Int): ResultWrapper<List<ProductModel>, CommonErrorModel>{
        return feedRepository.getProducts(limit, skip)
    }
}