package com.example.foodtest.domain

import com.example.foodtest.ui.model.ProductDtoToUiMapper
import com.example.foodtest.ui.model.ProductItem
import com.example.foodtest.utils.ui.Category

class GetProductsUseCase(private val repository: ProductRepository) {

    suspend fun getProductList(category: Category) : Result<List<ProductItem>> {
        val result = repository.getProductData(category)
        return result.map { responseDto ->
            ProductDtoToUiMapper(responseDto, category)
        }
    }
}