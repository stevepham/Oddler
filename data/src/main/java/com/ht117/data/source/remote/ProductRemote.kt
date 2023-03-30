package com.ht117.data.source.remote

import android.util.Log
import com.ht117.data.getRequest
import com.ht117.data.handleError
import com.ht117.data.model.DataResponse
import com.ht117.data.model.Product
import com.ht117.data.model.Request
import com.ht117.data.patchRequest
import com.ht117.data.postRequest
import com.ht117.data.deleteRequest
import com.ht117.data.source.local.ConfigLocal
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.flow

class ProductRemote(private val client: HttpClient) {

    fun getProductList() = flow {
        val endPoint = PRODUCT_LIST_URL.format(ConfigLocal.getAccountID())
        val url = "${Remote.Host}$endPoint"
        getRequest<DataResponse<List<Product>>, List<Product>>(client, url) {
            it.data
        }
    }.handleError()

    fun addProduct(request: Request.AddProductRequest) = flow {
        val endPoint = ADD_PRODUCT_URL.format(ConfigLocal.getAccountID())
        val url = "${Remote.Host}$endPoint"
        postRequest(client, url, request)
    }.handleError()

    fun updateProduct(request: Request.UpdateProductRequest) = flow {
        val endPoint = UPDATE_PRODUCT_URL.format(ConfigLocal.getAccountID(), request.name)
        val url = "${Remote.Host}$endPoint"
        patchRequest(client, url, request)
    }.handleError()

    fun deleteProduct(productName: String) = flow {
        val endPoint = DELETE_PRODUCT_URL.format(ConfigLocal.getAccountID(), productName)
        val url = "${Remote.Host}$endPoint"
        deleteRequest(client, url)
    }.handleError()

    companion object {
        private const val PRODUCT_LIST_URL = "/api/accounts/%s/products"
        private const val ADD_PRODUCT_URL = "/api/accounts/%s/products"
        private const val UPDATE_PRODUCT_URL = "/api/accounts/%s/products/%s/discount"
        private const val DELETE_PRODUCT_URL = "/api/accounts/%s/products/%s"
    }
}
