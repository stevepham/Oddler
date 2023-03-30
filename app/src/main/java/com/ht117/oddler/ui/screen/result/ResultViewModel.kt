package com.ht117.oddler.ui.screen.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.Request
import com.ht117.data.model.UiState
import com.ht117.data.repo.IProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ResultViewModel(private val productRepo: IProductRepo): ViewModel() {

    private val _addUiState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val addUiState: StateFlow<UiState<Boolean>>
        get() = _addUiState

    private val _updateUiState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val updateUiState: StateFlow<UiState<Boolean>>
        get() = _updateUiState

    fun addProduct(req: Request.AddProductRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.addProduct(req)
                .onEach {
                    _addUiState.emit(it)
                }
                .collect()
        }
    }

    fun updateProduct(req: Request.UpdateProductRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepo.updateProduct(req)
                .onEach {
                    _updateUiState.emit(it)
                }
                .collect()
        }
    }
}
