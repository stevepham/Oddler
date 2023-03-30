package com.ht117.oddler.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.AppErr
import com.ht117.data.model.Product
import com.ht117.data.model.UiState
import com.ht117.data.repo.IProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class DeleteProductState(val product: Product? = null,
                              val state: UiState<Boolean> = UiState.Init)

data class ProductListState(val productState: UiState<List<Product>>,
                            val deleteProductState: DeleteProductState)

class ProductListViewModel(private val productRepo: IProductRepo): ViewModel() {

    private val _uiState = MutableSharedFlow<ProductListState>(replay = 1)

    val uiState: SharedFlow<ProductListState>
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
            .onStart { _uiState.emit(ProductListState(UiState.Loading, DeleteProductState())) }
            .flowOn(Dispatchers.IO)
            .collect {
                _uiState.emit(ProductListState(it, DeleteProductState()))
            }
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        productRepo.deleteProduct(product.name)
            .onStart {
                val last = _uiState.replayCache.last()
                _uiState.emit(last.copy(deleteProductState = DeleteProductState(product, UiState.Loading)))
            }
            .flowOn(Dispatchers.IO)
            .collect {
                val last = _uiState.replayCache.last()

                if (it !is UiState.Success) {
                    _uiState.emit(
                        last.copy(
                            deleteProductState = last.deleteProductState.copy(
                                state = it
                            )
                        )
                    )
                } else {
                    if (it.data) {
                        val products = (last.productState as UiState.Success).data.toMutableList()
                        products.remove(product)
                        _uiState.emit(
                            last.copy(
                                productState = UiState.Success(products),
                                deleteProductState = DeleteProductState()
                            )
                        )
                    } else {
                        _uiState.emit(last.copy(deleteProductState = DeleteProductState(product, UiState.Failed(AppErr.UnknownErr(Throwable())))))
                    }
                }
            }
    }
}
