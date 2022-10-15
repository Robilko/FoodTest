package com.example.foodtest.di

import androidx.room.Room
import com.example.foodtest.data.LocalProductRepoImpl
import com.example.foodtest.data.RemoteProductRepoImpl
import com.example.foodtest.data.api.ApiService
import com.example.foodtest.data.db.DbUtils.DB_NAME
import com.example.foodtest.data.db.ProductsDataBase
import com.example.foodtest.domain.GetProductsUseCase
import com.example.foodtest.domain.LocalRepository
import com.example.foodtest.domain.RemoteRepository
import com.example.foodtest.ui.viewmodel.MainMenuViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { ApiService.getInstance() }
    single { Room.databaseBuilder(androidApplication(), ProductsDataBase::class.java, DB_NAME).build() }
    single<RemoteRepository> { RemoteProductRepoImpl(apiService = get()) }
    single<LocalRepository> { LocalProductRepoImpl(dataBase = get()) }
    single { GetProductsUseCase(remoteRepository = get(), localRepository = get()) }
    viewModel { MainMenuViewModel(useCases = get()) }
}