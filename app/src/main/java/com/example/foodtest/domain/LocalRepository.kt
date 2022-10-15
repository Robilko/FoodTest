package com.example.foodtest.domain

import com.example.foodtest.data.db.entity.ProductEntity
import com.example.foodtest.ui.model.ProductItem
import com.example.foodtest.utils.ui.Category

interface LocalRepository {
    suspend fun getProductData(category: Category): Result<List<ProductEntity>>
    suspend fun addProductToCache(list: List<ProductItem>): Result<Boolean>
    suspend fun clearAllProductCache(): Result<Boolean>
}