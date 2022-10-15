package com.example.foodtest.domain

import com.example.foodtest.data.api.model.ProductDTO
import com.example.foodtest.utils.ui.Category

interface ProductRepository {
    suspend fun getProductData(category: Category) : Result<List<ProductDTO>>
}