package com.ht117.data.model

import kotlinx.serialization.Serializable

sealed interface UiState<out T> {
    object Init: UiState<Nothing>
    object Loading: UiState<Nothing>
    data class Success<T>(val data: T): UiState<T>
    data class Failed(val err: AppErr): UiState<Nothing>
}

@Serializable
data class Product(
    val name: String,
    val price: Float,
    val discount: Float
)

fun Product.getDiscountedPrice(): String {
    val discountedPrice = (100 - discount) / 100F * price

    return "$$discountedPrice"
}

fun Product.getRawPrice(): String {
    return "$$price"
}
