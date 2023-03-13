package com.ht117.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(
    val data: T,
    val error: String? = null
)

@Serializable
data class ResultResponse(val success: Boolean, val error: String? = null)
