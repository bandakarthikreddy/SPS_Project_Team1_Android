package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.CustomerQueryDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerQueryAPI {

    @GET("api/CustomerQuery")
    suspend fun getCustomerQuery() : Response<List<CustomerQueryDataClassItem>>

    @GET("api/CustomerQuery/{queryId}")
    suspend fun getCustomerQueryById(
        @Path("queryId") queryId : String
    ) : Response<CustomerQueryDataClassItem>

    @POST("api/CustomerQuery/{queryId}")
    suspend fun addCustomerQuery(
        @Path("queryId") queryId: String,
        @Body customerQuery: CustomerQueryDataClassItem
    ) : Response<CustomerQueryDataClassItem>

    @PUT("api/CustomerQuery/{queryId}")
    suspend fun updateCustomerQuery(
        @Path("queryId") queryId: String,
        @Body customerQuery: CustomerQueryDataClassItem
    ) : Response<CustomerQueryDataClassItem>

    @DELETE("api/CustomerQuery/{queryId}")
    suspend fun deleteCustomerQuery(
        @Path("queryId") queryId : String
    ) : Response<Void>
}