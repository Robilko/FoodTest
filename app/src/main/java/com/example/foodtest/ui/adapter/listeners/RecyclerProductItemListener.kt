package com.example.foodtest.ui.adapter.listeners

import com.example.foodtest.ui.model.ProductItem

interface RecyclerProductItemListener {
    fun onItemClick(item: ProductItem)
    fun onOrderBtnClick(item: ProductItem)
}