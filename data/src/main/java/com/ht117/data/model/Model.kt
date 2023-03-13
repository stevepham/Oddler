package com.ht117.data.model

sealed class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
    data class Failed(val err: AppErr): UiState<Nothing>()
}

data class Product(
    val name: String,
    val price: Double,
    val discount: Int
)
