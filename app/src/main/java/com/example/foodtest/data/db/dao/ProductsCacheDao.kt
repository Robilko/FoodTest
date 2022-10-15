package com.example.foodtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodtest.data.db.entity.ProductEntity

@Dao
interface ProductsCacheDao {
    @Query("SELECT * FROM products_table")
    suspend fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(list: List<ProductEntity>)

    @Query("DELETE FROM products_table")
    suspend fun removeAll()
}