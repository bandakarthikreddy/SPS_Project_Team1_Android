package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.ProductAddonDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductAddonAPI {
    @GET("api/ProductAddOn")
    suspend fun getProductAddOn() : Response<List<ProductAddonDataClassItem>>

    @POST("api/ProductAddOn/{Token}")
    suspend fun addProductAddOn(
        @Body productAddOn: ProductAddonDataClassItem,
    ) : Response<ProductAddonDataClassItem>

    @GET("api/ProductAddOn/{productID}/{addonId}")
    suspend fun getProductAddOnByID(
        @Path("productID") productID: String,
        @Path("addonId") addonId: String
    ) : Response<ProductAddonDataClassItem>

    @PUT("api/ProductAddOn/{productID}/{addonId}")
    suspend fun updateProductAddOn(
        @Path("productID") productID : String,
        @Path("addonId") addonId: String,
        @Body productAddOn: ProductAddonDataClassItem
    ) : Response<ProductAddonDataClassItem>

    @DELETE("api/ProductAddOn/{productID}/{addonId}")
    suspend fun deleteProductAddOn(
        @Path("productID") productID : String,
        @Path("addonId") addonId: String,
    ) : Response<Void>
}