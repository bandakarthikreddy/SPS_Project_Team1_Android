package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.CustomerDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerAPI {
    @GET("Customer")
    suspend fun getCustomer(): Response<List<CustomerDataClassItem>>

    @GET("Customer/{id}")
    suspend fun getCustomerById(@Path("id") id: String): Response<CustomerDataClassItem>

    @POST("Customer/{customerId}")
    suspend fun postCustomer(
        @Path("customerId") customerId: String,
        @Body customer: CustomerDataClassItem
    ): Response<CustomerDataClassItem>

    @PUT("Customer/{id}")
    suspend fun updateCustomer(@Path("id") id: String, @Body customer: CustomerDataClassItem): Response<CustomerDataClassItem>

    @DELETE("Customer/{id}")
    suspend fun deleteCustomer(@Path("id") id: String): Response<Void>
}