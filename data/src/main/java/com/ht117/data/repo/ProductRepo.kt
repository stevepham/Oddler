package com.ht117.data.repo

import com.ht117.data.model.Product
import com.ht117.data.model.Request
import com.ht117.data.model.UiState
import com.ht117.data.source.remote.ProductRemote
import kotlinx.coroutines.flow.Flow

interface IProductRepo{
    fun getProductList(): Flow<UiState<List<Product>>>

    fun addProduct(request: Request.AddProductRequest): Flow<UiState<Boolean>>

    fun updateProduct(request: Request.UpdateProductRequest): Flow<UiState<Boolean>>
}

class ProductRepoImpl(private val remote: ProductRemote): IProductRepo {
    override fun getProductList() = remote.getProductList()

    override fun addProduct(request: Request.AddProductRequest) = remote.addProduct(request)

    override fun updateProduct(request: Request.UpdateProductRequest) = remote.updateProduct(request)

}
