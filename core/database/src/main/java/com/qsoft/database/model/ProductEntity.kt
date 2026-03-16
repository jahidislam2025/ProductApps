package com.qsoft.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productID: Int = 0,
    val title: String?,
    val description: String?,
    val photoUrl: String?,
    @ColumnInfo(defaultValue = "false")
    val isFavorite: Boolean = false
)*/

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val description: String,
    val image: String,
    @ColumnInfo(defaultValue = "false")
    val isFavorite: Boolean = false
)
