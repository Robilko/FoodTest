package com.example.foodtest.domain

import com.example.foodtest.ui.model.ProductDtoToUiMapper
import com.example.foodtest.ui.model.ProductEntityToUiMapper
import com.example.foodtest.ui.model.ProductItem
import com.example.foodtest.utils.ui.Category

class GetProductsUseCase(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) {

    suspend fun getProductListFromRemote(category: Category) : Result<List<ProductItem>> {
        val result = remoteRepository.getProductData(category)
        return result.map { responseDto ->
            ProductDtoToUiMapper(responseDto, category)
        }
    }

    suspend fun getProductListFromLocalDB(category: Category) : Result<List<ProductItem>> {
        val result = localRepository.getProductData(category)
        return result.map { productList ->
            ProductEntityToUiMapper(productList)
        }
    }

    suspend fun addProductListToDb(list: List<ProductItem>) : Result<Boolean> {
        return try {
            localRepository.addProductToCache(list)
            Result.success(value = true)
        } catch (ex: Exception) {
            Result.failure(exception = ex)
        }
    }

    suspend fun clearProductsFromCache() : Result<Boolean> {
        return try {
            localRepository.clearAllProductCache()
            Result.success(value = true)
        } catch (ex: Exception) {
            Result.failure(exception = ex)
        }
    }
}