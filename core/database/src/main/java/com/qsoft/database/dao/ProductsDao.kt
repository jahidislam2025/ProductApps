package com.qsoft.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qsoft.database.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("DELETE  FROM product")
    suspend fun deleteProducts()

    // @Query("DELETE FROM sqlite_sequence WHERE name='product'")
    /*@Query("DELETE FROM sqlite_sequence")
    suspend fun resetPrimaryKey()*/


    @Query(
        """
        SELECT * FROM product WHERE (:searchKey = '' OR title LIKE '%' || :searchKey || '%' COLLATE NOCASE
           OR description LIKE '%' || :searchKey || '%' COLLATE NOCASE)
           """
    )
    fun getProducts(searchKey: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE isFavorite = 1")
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

    @Query("UPDATE product SET isFavorite = :isFavorite WHERE productID =:id")
    fun updateIsFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT count(*) from product where isFavorite = 1")
    fun getFavoriteCount(): Flow<Int>
}