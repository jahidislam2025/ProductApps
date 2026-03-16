package com.qsoft.database.di

import android.app.Application
import androidx.room.Room
import com.qsoft.database.AppDatabase
import com.qsoft.database.dao.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    private val DB_NAME = "task_db"

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesProductsDao(db: AppDatabase): ProductsDao = db.productsDao
}