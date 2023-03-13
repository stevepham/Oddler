package com.ht117.data.source.remote

import com.ht117.data.getRequest
import com.ht117.data.handleError
import com.ht117.data.model.AddProductRequest
import com.ht117.data.model.DataResponse
import com.ht117.data.model.Product
import com.ht117.data.model.UpdateProductRequest
import com.ht117.data.patchRequest
import com.ht117.data.postRequest
import com.ht117.data.source.local.ConfigLocal
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.flow

class ProductRemote(private val client: HttpClient) {

    fun getProductList() = flow {
        val endPoint = PRODUCT_LIST_URL.format(ConfigLocal.getAccountID())
        val url = "${Remote.Host}$endPoint"
        getRequest<DataResponse<List<Product>>, List<Product>>(client, url) {
            it.data
        }
    }.handleError()

    fun addProduct(request: AddProductRequest) = flow {
        val endPoint = ADD_PRODUCT_URL.format(ConfigLocal.getAccountID())
        val url = "${Remote.Host}$endPoint"
        postRequest(client, url, request)
    }.handleError()

    fun updateProduct(request: UpdateProductRequest) = flow {
        val endPoint = UPDATE_PRODUCT_URL.format(ConfigLocal.getAccountID(), request.name)
        val url = "${Remote.Host}$endPoint"
        patchRequest(client, url, request)
    }.handleError()

    companion object {
        private const val PRODUCT_LIST_URL = "/api/accounts/%s/products"
        private const val ADD_PRODUCT_URL = "/api/accounts/%s/products"
        private const val UPDATE_PRODUCT_URL = "/api/accounts/%s/products/%s/discount"
    }
}
