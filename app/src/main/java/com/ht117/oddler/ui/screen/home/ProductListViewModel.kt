package com.ht117.oddler.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.Product
import com.ht117.data.model.UiState
import com.ht117.data.repo.IConfigRepo
import com.ht117.data.repo.IProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

data class ProductListState(val products: UiState<List<Product>>, val userID: String)

class HomeViewModel(private val productRepo: IProductRepo,
                    private val configRepo: IConfigRepo): ViewModel() {

    init {
        viewModelScope.launch {
            productRepo.getProductList()
                .combine(configRepo.getUserID()) { productState, userID ->
                    ProductListState(productState, userID)
                }
                .flowOn(Dispatchers.IO)
        }
    }

    val uiState: SharedFlow<ProductListState>
        get() = productRepo.getProductList()
            .combine(configRepo.getUserID()) { productState, userID ->
                ProductListState(productState, userID)
            }
            .flowOn(Dispatchers.IO)
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly
            )
}
