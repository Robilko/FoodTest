package com.example.foodtest.ui.model

sealed class ListViewState {
    object Empty : ListViewState()
    object Loading : ListViewState()
    data class Data(val data: List<ProductItem>) : ListViewState()
    data class Error(var message: String?) : ListViewState()
}