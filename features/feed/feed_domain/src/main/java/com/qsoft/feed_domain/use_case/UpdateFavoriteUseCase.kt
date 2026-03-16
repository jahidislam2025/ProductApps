package com.qsoft.feed_domain.use_case

import com.qsoft.feed_domain.repository.FeedRepository

class UpdateFavoriteUseCase(private val feedRepository: FeedRepository) {
    suspend operator fun invoke(productId: Int, isFavorite: Boolean) {
        return feedRepository.updateFavorite(productId, isFavorite)
    }
}