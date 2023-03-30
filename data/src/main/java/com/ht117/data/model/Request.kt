package com.ht117.data.model

import kotlinx.serialization.Serializable

sealed class Request {
    @Serializable
    data class AddProductRequest(
        val name: String,
        val price: Float,
        val discount: Float
    ) : Request()

    @Serializable
    data class UpdateProductRequest(
        @Transient
        val name: String,
        val discount: Float
    ) : Request()
}
