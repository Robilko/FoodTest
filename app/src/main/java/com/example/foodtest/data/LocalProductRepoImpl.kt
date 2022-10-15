package com.example.foodtest.data

import com.example.foodtest.data.db.ProductsDataBase
import com.example.foodtest.data.db.entity.ProductEntity
import com.example.foodtest.domain.LocalRepository
import com.example.foodtest.ui.model.ProductItem
import com.example.foodtest.ui.model.ProductItemToDbMapper
import com.example.foodtest.utils.ui.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalProductRepoImpl(private val dataBase: ProductsDataBase): LocalRepository {

    override suspend fun getProductData(category: Category): Result<List<ProductEntity>> {
        return try {
            val productList = withContext(Dispatchers.IO) {
                dataBase.productsCacheDao().getAll()
            }
            Result.success(value = productList)
        } catch (ex: Exception) {
            Result.failure(exception = ex)
        }
    }

    override suspend fun addProductToCache(list: List<ProductItem>) : Result<Boolean> {
        return try {
            val entityList = ProductItemToDbMapper(list)
            dataBase.productsCacheDao().saveAll(entityList)
            Result.success(value = true)
        } catch (ex: Exception) {
            Result.failure(exception = ex)
        }
    }

    override suspend fun clearAllProductCache(): Result<Boolean> {
        return try {
            dataBase.productsCacheDao().removeAll()
            Result.success(value = true)
        } catch (ex: Exception) {
            Result.failure(exception = ex)
        }
    }
}