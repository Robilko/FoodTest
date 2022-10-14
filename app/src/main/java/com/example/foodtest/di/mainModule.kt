package com.example.foodtest.di

import com.example.foodtest.ui.viewmodel.MainMenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainMenuViewModel() }
}