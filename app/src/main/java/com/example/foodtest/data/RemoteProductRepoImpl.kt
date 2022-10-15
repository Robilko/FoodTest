package com.example.foodtest.data

import com.example.foodtest.data.api.ApiService
import com.example.foodtest.data.api.model.ProductDTO
import com.example.foodtest.domain.RemoteRepository
import com.example.foodtest.utils.ui.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RemoteProductRepoImpl(private val apiService: ApiService) : RemoteRepository {

    override suspend fun getProductData(category: Category): Result<List<ProductDTO>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                when (category) {
                    Category.BURGERS -> apiService.getBurgers()
                    Category.PIZZAS -> apiService.getPizzas()
                    Category.SAUSAGES -> apiService.getSausages()
                    Category.STEAKS -> apiService.getSteaks()
                    Category.SANDWICHES -> apiService.getSandwiches()
                    Category.DESSERTS -> apiService.getDesserts()
                    Category.DRINKS -> apiService.getDrinks()
                }
            }
            Result.success(value = response)

        } catch (ex: HttpException) {
            Result.failure(exception = ex)
        } catch (ex: IOException) {
            Result.failure(exception = ex)
        }
    }
}