package com.qsoft.feed_domain.use_case

import com.qsoft.feed_domain.repository.FeedRepository

class DeleteProductsUseCase(private val feedRepository: FeedRepository) {
    suspend operator fun invoke(){
        return feedRepository.deleteProducts()
    }
}