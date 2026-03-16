package com.qsoft.feed_domain.use_case

import com.qsoft.feed_domain.model.ProductModel
import com.qsoft.feed_domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow


class GetFavoritesProductsUseCase(private val feedRepository: FeedRepository) {
    operator fun invoke(): Flow<List<ProductModel>> {
        return feedRepository.getFavoriteProducts()
    }
}