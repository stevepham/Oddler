package com.ht117.oddler.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.Product
import com.ht117.data.model.UiState
import com.ht117.data.repo.IProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProductListViewModel(private val productRepo: IProductRepo): ViewModel() {

    private val _uiState = MutableSharedFlow<UiState<List<Product>>>(replay = 1)

    val uiState: SharedFlow<UiState<List<Product>>>
        get() = _uiState

    init {
        getProducts()
    }

    fun refresh() {
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch {
        _uiState.resetReplayCache()
        productRepo.getProductList()
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .onStart {
                _uiState.emit(UiState.Loading)
            }
            .onEach {
                _uiState.emit(it)
            }.collect()
    }
}
