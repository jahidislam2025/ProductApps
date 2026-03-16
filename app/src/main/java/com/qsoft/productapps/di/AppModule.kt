package com.qsoft.productapps.di

import android.content.Context
import com.qsoft.network.utils.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideNetworkHandler(
        @ApplicationContext context: Context,
    ): NetworkHandler {
        return NetworkHandler(context)
    }
}