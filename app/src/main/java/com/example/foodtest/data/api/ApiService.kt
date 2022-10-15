package com.example.foodtest.data.api

import com.example.foodtest.data.api.model.ProductDTO
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("burgers")
    suspend fun getBurgers(): List<ProductDTO>

    @GET("pizzas")
    suspend fun getPizzas(): List<ProductDTO>

    @GET("sausages")
    suspend fun getSausages(): List<ProductDTO>

    @GET("steaks")
    suspend fun getSteaks(): List<ProductDTO>

    @GET("desserts")
    suspend fun getDesserts(): List<ProductDTO>

    @GET("sandwiches")
    suspend fun getSandwiches(): List<ProductDTO>

    @GET("drinks")
    suspend fun getDrinks(): List<ProductDTO>

    companion object {
        private const val BASE_URL = "https://ig-food-menus.herokuapp.com/"

        fun getInstance(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
        }
    }
}