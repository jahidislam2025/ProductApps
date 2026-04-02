package com.qsoft.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qsoft.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // ← REPLACE থেকে IGNORE করুন
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM product")
    suspend fun deleteProducts()

    @Query(
        """
        SELECT * FROM product WHERE (:searchKey = '' OR title LIKE '%' || :searchKey || '%' COLLATE NOCASE
           OR description LIKE '%' || :searchKey || '%' COLLATE NOCASE)
           """
    )
    fun getProducts(searchKey: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE isFavorite = 1")
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

/*    @Query("UPDATE product SET isFavorite = :isFavorite WHERE productId = :id")
    fun updateIsFavorite(id: Int, isFavorite: Boolean)*/


    @Query("UPDATE product SET isFavorite = :isFavorite WHERE productId = :id")
    suspend fun updateIsFavorite(id: Int, isFavorite: Int)

    @Query("SELECT count(*) from product where isFavorite = 1")
    fun getFavoriteCount(): Flow<Int>

    @Query("UPDATE product SET isFavorite = 0 WHERE isFavorite = 1")
    suspend fun clearAllFavorites()

    @Query("DELETE FROM product WHERE isFavorite = 0") // ← নতুন যোগ
    suspend fun deleteNonFavoriteProducts()
}