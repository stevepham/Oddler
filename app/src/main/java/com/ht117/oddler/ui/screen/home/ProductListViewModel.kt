package com.ht117.oddler.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.Product
import com.ht117.data.model.UiState
import com.ht117.data.repo.IProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn

class ProductListViewModel(private val productRepo: IProductRepo): ViewModel() {

    val uiState: SharedFlow<UiState<List<Product>>>
        get() = productRepo.getProductList()
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly
            )
}
