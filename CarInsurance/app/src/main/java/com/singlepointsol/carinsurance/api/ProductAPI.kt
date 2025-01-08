package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.ProductDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductAPI {
    @GET("api/Product")
    suspend fun getProduct(): Response<List<ProductDataClassItem>>

    @POST("api/Product/{productID}")
    suspend fun addProduct(
        @Path("productID") productID: String,
        @Body product: ProductDataClassItem
    ): Response<ProductDataClassItem>

    @GET("api/Product/{productID}")
    suspend fun getProductById(
        @Path("productID") productID: String
    ): Response<ProductDataClassItem>

    @PUT("api/Product/{productID}")
    suspend fun updateProduct(
        @Path("productID") productID: String,
        @Body product: ProductDataClassItem
    ): Response<Void>


    @DELETE("api/Product/{productID}")
    suspend fun deleteProduct(
        @Path("productID") productID: String
    ): Response<Void>
}