package com.qsoft.feed_data.repository

import com.qsoft.feed_data.dataSource.local.FeedLocalDataSource
import com.qsoft.feed_data.dataSource.remote.FeedRemoteDataSource
import com.qsoft.feed_data.mapper.toEntity
import com.qsoft.feed_data.mapper.toResponse
import com.qsoft.feed_domain.model.ProductModel
import com.qsoft.feed_domain.repository.FeedRepository
import com.qsoft.network.model.CommonErrorModel
import com.qsoft.network.utils.ExceptionalMessage
import com.qsoft.network.utils.NetworkHandler
import com.qsoft.network.utils.ResultWrapper
import com.qsoft.network.utils.parseHttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException

class FeedRepositoryImpl(
    private val feedLocalDataSource: FeedLocalDataSource,
    private val feedRemoteDataSource: FeedRemoteDataSource,
    private val networkHandler: NetworkHandler
) : FeedRepository {
    override suspend fun getProducts(
        limit: Int,
        skip: Int
    ): ResultWrapper<List<ProductModel>, CommonErrorModel> {
        return if (networkHandler.isNetworkAvailable()) {
            try {

                val productsDto =
                    feedRemoteDataSource.getProducts(limit, skip)

                val response = productsDto.products.map { it.toResponse() }


                feedLocalDataSource.saveProducts(response.map { it.toEntity() })

                return ResultWrapper.Success(response)
            } catch (e: HttpException) {
                val errorResponse = parseHttpException(e)
                ResultWrapper.Failure(errorResponse)

            } catch (e: Exception) {
                ResultWrapper.Failure(
                    CommonErrorModel(
                        message = e.message.toString(),
                    )
                )
            }
        } else {
            ResultWrapper.Failure(CommonErrorModel(message = ExceptionalMessage.INTERNET_NOT_AVAILABLE))
        }
    }

    override suspend fun deleteProducts() {
        if (networkHandler.isNetworkAvailable()) {
            feedLocalDataSource.deleteUsers()
            //feedLocalDataSource.resetPrimaryKey()
        }
    }

    override fun getLocalProducts(searchKey: String): Flow<List<ProductModel>> {
        return feedLocalDataSource
            .getProducts(searchKey)
            .map { list -> list.map { it.toResponse() } }
    }

    override fun getFavoriteProducts(): Flow<List<ProductModel>> {
        return feedLocalDataSource
            .getFavoriteProducts()
            .map { list -> list.map { it.toResponse() } }
    }

    override fun getFavoriteProductsCount(): Flow<Int> {
        return feedLocalDataSource
            .getFavoriteProductsCount()
    }

    override suspend fun updateFavorite(productId: Int, isFavorite: Boolean) {
        feedLocalDataSource.updateIsFavorite(id = productId, isFavorite = isFavorite)
    }


}