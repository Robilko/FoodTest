package com.example.foodtest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtest.domain.GetProductsUseCase
import com.example.foodtest.ui.model.ListViewState
import com.example.foodtest.ui.model.ProductItem
import com.example.foodtest.utils.ui.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainMenuViewModel(private val useCases: GetProductsUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow<ListViewState>(ListViewState.Loading)
    val viewState: StateFlow<ListViewState> = _viewState.asStateFlow()

    private val products = mutableListOf<ProductItem>()

    fun getData(category: Category, isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            getDataFromRemote(category)
        } else {
            getDataFromCacheDb(category)
        }
    }

    private fun getDataFromRemote(category: Category) {
        viewModelScope.launch {
            useCases.getProductListFromRemote(category = category)
                .onSuccess { productItemList ->
                    if (productItemList.isEmpty()) {
                        _viewState.value = ListViewState.Empty
                    } else {
                        products += productItemList
                        useCases.addProductListToDb(productItemList)
                        _viewState.value = ListViewState.Data(data = productItemList)
                    }
                }
                .onFailure { _viewState.value = ListViewState.Error(message = it.message) }
        }
    }

    private fun getDataFromCacheDb(category: Category) {
        viewModelScope.launch {
            useCases.getProductListFromLocalDB(category)
                .onSuccess { productItemList ->
                    if (productItemList.isEmpty()) {
                        _viewState.value = ListViewState.Empty
                    } else{
                        products.addAll(productItemList)
                        getDataByCategory(category, false)
                    }
                }
                .onFailure { error ->
                    _viewState.value = ListViewState.Error(message = error.message)
                }
        }
    }

    fun getDataByCategory(category: Category, isNetworkAvailable: Boolean) {
        _viewState.value = ListViewState.Loading
        val filteredByCategory = products.filter { it.category == category }
        if (filteredByCategory.isEmpty()) {
            if(isNetworkAvailable) {
                getDataFromRemote(category)
            } else {
                _viewState.value = ListViewState.Empty
            }
        } else {
            _viewState.value = ListViewState.Data(data = filteredByCategory)
        }
    }

//    Not currently used, but will be needed to implement conditional cache flushing.
    fun clearProductCache() {
        viewModelScope.launch { useCases.clearProductsFromCache() }
    }
}