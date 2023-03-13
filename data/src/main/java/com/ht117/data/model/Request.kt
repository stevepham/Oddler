package com.ht117.data.model

data class AddProductRequest(
    val name: String,
    val price: Double,
    val discount: Int
)

data class UpdateProductRequest(
    @Transient val name: String,
    val discount: Int
)
