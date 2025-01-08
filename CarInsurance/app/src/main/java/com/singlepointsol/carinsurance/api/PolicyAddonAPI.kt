package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.PolicyAddonDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PolicyAddonAPI {
    @GET("api/PolicyAddon")
    suspend fun getPolicyAddon() : Response<List<PolicyAddonDataClassItem>>

    @POST("api/PolicyAddon/{Token}")
    suspend fun addPolicyAddon(
        @Body policyAddon: PolicyAddonDataClassItem,
    ) : Response<PolicyAddonDataClassItem>


    @GET("api/PolicyAddon/{policyNo}/{addonID}")
    suspend fun getPolicyAddonByID(
        @Path("policyNo") policyNo: String,
        @Path("addonID") addonID: String,
    ) : Response<PolicyAddonDataClassItem>

    @PUT("api/PolicyAddon/{policyNo}/{addonID}")
    suspend fun updatePolicyAddon(
        @Path("policyNo") policyNo : String,
        @Path("addonID") addonID: String,
        @Body policyAddon: PolicyAddonDataClassItem
    ) : Response<PolicyAddonDataClassItem>

    @DELETE("api/PolicyAddon/{policyNo}/{addonID}")
    suspend fun deletePolicyAddon(
        @Path("policyNo") policyNo : String,
        @Path("addonID") addonID: String,
    ) : Response<Void>
}