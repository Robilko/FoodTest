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

class MainMenuViewModel(private val useCase: GetProductsUseCase) : ViewModel() {

    private val _viewState = MutableStateFlow<ListViewState>(ListViewState.Loading)
    val viewState: StateFlow<ListViewState> = _viewState.asStateFlow()

    private val products = mutableListOf<ProductItem>()


    fun getDataFromRemote(category: Category) {
        viewModelScope.launch {
            useCase.getProductList(category = category)
                .onSuccess {
                    if (it.isEmpty()) {
                        _viewState.value = ListViewState.Empty
                    } else {
                        products += it
                        _viewState.value = ListViewState.Data(data = it)
                    }
                }
                .onFailure { _viewState.value = ListViewState.Error(message = it.message) }
        }
    }

    fun getDataByCategory(category: Category) {
        _viewState.value = ListViewState.Loading
        val filteredByCategory = products.filter { it.category == category }
        if (filteredByCategory.isEmpty()) {
            getDataFromRemote(category)
        } else {
            _viewState.value = ListViewState.Data(data = filteredByCategory)
        }
    }
}