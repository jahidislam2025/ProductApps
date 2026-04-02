package com.qsoft.feed_data.dataSourceImpl.local

import com.qsoft.common.util.CoroutineDispatcherProvider
import com.qsoft.database.dao.ProductsDao
import com.qsoft.database.model.ProductEntity
import com.qsoft.feed_data.dataSource.local.FeedLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FeedLocalDataSourceImpl(
    private val productsDao: ProductsDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : FeedLocalDataSource {
    override suspend fun saveProducts(products: List<ProductEntity>) =
        withContext(coroutineDispatcherProvider.io) {
            productsDao.insertProducts(products)
        }

    override fun getProducts(searchKey: String): Flow<List<ProductEntity>> =
        productsDao.getProducts(searchKey)

    override fun getFavoriteProducts(): Flow<List<ProductEntity>> =
        productsDao.getFavoriteProducts()

    override fun getFavoriteProductsCount(): Flow<Int> = productsDao.getFavoriteCount()

    override suspend fun deleteUsers() = withContext(coroutineDispatcherProvider.io) {
        productsDao.deleteProducts()
    }


/*    override suspend fun updateIsFavorite(id: Int, isFavorite: Boolean) =
        withContext(coroutineDispatcherProvider.io) {
            productsDao.updateIsFavorite(id = id, isFavorite = isFavorite)
        }*/

    override suspend fun updateIsFavorite(id: Int, isFavorite: Boolean) =
        withContext(coroutineDispatcherProvider.io) {
            productsDao.updateIsFavorite(
                id = id,
                isFavorite = if (isFavorite) 1 else 0  // ← Boolean → Int
            )
        }

    // ← নতুন: WorkManager থেকে call হয়, সব favorite clear করে
    override suspend fun clearAllFavorites() =
        withContext(coroutineDispatcherProvider.io) {
            productsDao.clearAllFavorites()
        }

    // ← নতুন যোগ করুন
    override suspend fun deleteNonFavoriteProducts() =
        withContext(coroutineDispatcherProvider.io) {
            productsDao.deleteNonFavoriteProducts()
        }
}