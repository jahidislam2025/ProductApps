package com.qsoft.feed_domain.use_case

import com.qsoft.feed_domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteProductsCountUseCase(private val feedRepository: FeedRepository) {
    operator fun invoke(): Flow<Int> {
        return feedRepository.getFavoriteProductsCount()
    }
}