package com.example.foodtest.ui.model

import com.example.foodtest.utils.ui.Category

data class ProductItem(
    val id: String,
    val imageUrl: String,
    val price: String,
    val title: String,
    val description: String,
    val category: Category
)