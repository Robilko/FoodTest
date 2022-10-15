package com.example.foodtest.data.api.model


import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("country")
    val country: String,
)