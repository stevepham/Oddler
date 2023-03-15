package com.ht117.data.model

sealed class Request {
    data class AddProductRequest(
        val name: String,
        val price: Float,
        val discount: Float
    ) : Request()

    data class UpdateProductRequest(
        @Transient
        val name: String,
        val discount: Float
    ) : Request()
}
