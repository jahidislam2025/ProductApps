package com.qsoft.network.di

import android.content.Context
import com.qsoft.network.PublicApiService
import com.qsoft.network.di.qualifier.PublicNetwork
import com.qsoft.network.interceptor.PublicInterceptor
import com.qsoft.network.provider.BaseUrlProvider
import com.qsoft.network.utils.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PublicNetworkModule {


    @Provides
    @Singleton
    fun provideBaseUrlProvider(): BaseUrlProvider {
        return BaseUrlProvider()
    }

    @Provides
    @Singleton
    @PublicNetwork(TypeEnum.INTERCEPTOR)
    fun provideInterceptor(
        @ApplicationContext context: Context
    ): PublicInterceptor {
        return PublicInterceptor(
            context = context)
    }

    @Provides
    @Singleton
    @PublicNetwork(TypeEnum.OKHTTP)
    fun provideOkHttpClient(@PublicNetwork(TypeEnum.INTERCEPTOR) authInterceptor: PublicInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            this.addInterceptor(authInterceptor).addInterceptor(httpLoggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    @PublicNetwork(TypeEnum.RETROFIT)
    fun provideRetrofit(
        @PublicNetwork(TypeEnum.OKHTTP) okHttpClient: OkHttpClient,
        baseUrlProvider: BaseUrlProvider
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrlProvider.getBaseUrl()).callFactory(okHttpClient)
            .addCallAdapterFactory(ResultCallAdapterFactory()).addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }


    @Provides
    @Singleton
    @PublicNetwork(TypeEnum.SERVICE)
    fun providePublicApiService(
        @PublicNetwork(TypeEnum.RETROFIT) retrofit: Retrofit,
    ): PublicApiService {
        return retrofit.create(PublicApiService::class.java)
    }

}