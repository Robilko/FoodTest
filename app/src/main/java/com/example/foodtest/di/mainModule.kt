package com.example.foodtest.di

import com.example.foodtest.data.RemoteProductRepoImpl
import com.example.foodtest.data.api.ApiService
import com.example.foodtest.domain.GetProductsUseCase
import com.example.foodtest.domain.ProductRepository
import com.example.foodtest.ui.viewmodel.MainMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { ApiService.getInstance() }
    single<ProductRepository> { RemoteProductRepoImpl(apiService = get()) }
    single { GetProductsUseCase(repository = get()) }
    viewModel { MainMenuViewModel(useCase = get()) }
}