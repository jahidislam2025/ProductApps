package com.qsoft.feed_data.di

import android.content.Context
import com.qsoft.common.util.CoroutineDispatcherProvider
import com.qsoft.database.dao.ProductsDao
import com.qsoft.feed_data.dataSource.local.FeedLocalDataSource
import com.qsoft.feed_data.dataSource.remote.FeedRemoteDataSource
import com.qsoft.feed_data.dataSourceImpl.local.FeedLocalDataSourceImpl
import com.qsoft.feed_data.dataSourceImpl.remote.FeedRemoteDataSourceImpl
import com.qsoft.feed_data.repository.FeedRepositoryImpl
import com.qsoft.feed_data.worker.FavoriteClearScheduler
import com.qsoft.feed_data.worker.FavoriteClearSchedulerImpl
import com.qsoft.feed_domain.repository.FeedRepository
import com.qsoft.network.PublicApiService
import com.qsoft.network.di.TypeEnum
import com.qsoft.network.di.qualifier.PublicNetwork
import com.qsoft.network.utils.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FeedDataModule {
    @Singleton
    @Provides
    fun provideFeedRemoteDataSource(
        @PublicNetwork(TypeEnum.SERVICE) publicApiService: PublicApiService
    ): FeedRemoteDataSource {
        return FeedRemoteDataSourceImpl(publicApiService)
    }

    @Singleton
    @Provides
    fun providesFeedRepository(
        feedLocalDataSource: FeedLocalDataSource,
        feedRemoteDataSource: FeedRemoteDataSource,
        networkHandler: NetworkHandler,
        favoriteClearScheduler: FavoriteClearScheduler
    ): FeedRepository {
        return FeedRepositoryImpl(
            feedRemoteDataSource = feedRemoteDataSource,
            networkHandler = networkHandler,
            feedLocalDataSource = feedLocalDataSource,
            favoriteClearScheduler = favoriteClearScheduler
        )
    }

    @Singleton
    @Provides
    fun provideFeedLocalDataSource(
        productsDao: ProductsDao,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): FeedLocalDataSource {
        return FeedLocalDataSourceImpl(
            productsDao = productsDao,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }

    // ← নতুন যোগ করা হয়েছে
    @Singleton
    @Provides
    fun provideFavoriteClearScheduler(
        @ApplicationContext context: Context
    ): FavoriteClearScheduler {
        return FavoriteClearSchedulerImpl(context)
    }
}