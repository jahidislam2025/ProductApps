package com.qsoft.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val description: String,
    val image: String,
    val brand: String? = null,
    val price: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val category: String = "",
    val rating: Double = 0.0,
    val stock: Int = 0,
    @ColumnInfo(defaultValue = "false")
    val isFavorite: Boolean = false
)
