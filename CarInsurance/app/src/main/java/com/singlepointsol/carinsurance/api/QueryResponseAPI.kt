package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.QueryResponseDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface QueryResponseAPI {
    @GET("api/QueryResponse")
    suspend fun getQueryResponse() : Response<List<QueryResponseDataClassItem>>

    @GET("api/QueryResponse/{queryId}/{srNo}")
    suspend fun getQueryResponseById(
        @Path("queryId") queryId : String,
        @Path("srNo") srNo : String
    ) : Response<QueryResponseDataClassItem>

    @POST("api/QueryResponse/{Token}")
    suspend fun addQueryResponse(
        @Body queryResponse: QueryResponseDataClassItem
    ) : Response<QueryResponseDataClassItem>

    @PUT("api/QueryResponse/{queryId}/{srNo}")
    suspend fun updateQueryResponse(
        @Path("queryId") queryId: String,
        @Path("srNo") srNo : String,
        @Body queryResponse: QueryResponseDataClassItem
    ) : Response<QueryResponseDataClassItem>

    @DELETE("api/QueryResponse/{queryId}/{srNo}")
    suspend fun deleteQueryResponse(
        @Path("queryId") queryId : String,
        @Path("srNo") srNo : String
    ) : Response<Void>
}