package com.example.foodtest.utils.ui

import com.example.foodtest.R

enum class Category(val apiCode: String, val nameResId: Int) {
    BURGERS(apiCode = "burgers", nameResId = R.string.category_name_burgers),
    PIZZAS(apiCode = "pizzas", nameResId = R.string.category_name_pizzas),
    SAUSAGES(apiCode = "sausages", nameResId = R.string.category_name_sausages),
    STEAKS(apiCode = "steaks", nameResId = R.string.category_name_steaks),
    SANDWICHES(apiCode = "sandwiches", nameResId = R.string.category_name_sandwiches),
    DESSERTS(apiCode = "desserts", nameResId = R.string.category_name_desserts),
    DRINKS(apiCode = "drinks", nameResId = R.string.category_name_drinks),
}