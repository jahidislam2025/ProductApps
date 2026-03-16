package com.qsoft.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qsoft.database.dao.ProductsDao
import com.qsoft.database.model.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {
    abstract val productsDao: ProductsDao
}