package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.ClaimDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClaimAPI {

    @GET("Claim")
    suspend fun getClaim() : Response<List<ClaimDataClassItem>>

    @POST("Claim/{claimNo}")
    suspend fun addClaim(
        @Path("claimNo") claim: String,
        @Body claimNo: ClaimDataClassItem,
    ) : Response<ClaimDataClassItem>

    @GET("Claim/{claimNo}")
    suspend fun getClaimByClaimNo(
        @Path("claimNo") claimNo : String
    ) : Response<ClaimDataClassItem>

    @PUT("Claim/{claimNo}")
    suspend fun updateClaim(
        @Path("claimNo") claimNo: String,
        @Body claim : ClaimDataClassItem
    ) : Response<ClaimDataClassItem>

    @DELETE("Claim/{claimNo}")
    suspend fun deleteClaim(
        @Path("claimNo") claimNo: String
    ) : Response<Void>
}