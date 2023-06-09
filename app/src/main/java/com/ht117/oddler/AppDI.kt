package com.ht117.oddler

import com.ht117.oddler.ui.screen.HomeViewModel
import com.ht117.oddler.ui.screen.home.ProductListViewModel
import com.ht117.oddler.ui.screen.result.ResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ProductListViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ResultViewModel(get()) }
}
