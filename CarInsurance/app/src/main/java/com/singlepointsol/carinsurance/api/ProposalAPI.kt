package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.ProposalDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProposalAPI {
    @GET("Proposal")
    suspend fun getProposal() : Response<List<ProposalDataClassItem>>

    @POST("Proposal/{proposalNo}")
    suspend fun addProposal(
        @Path("proposalNo") proposalNo: String,
        @Body proposal: ProposalDataClassItem
    ) : Response<ProposalDataClassItem>

    @GET("Proposal/{proposalNo}")
    suspend fun getProposalByID(
        @Path("proposalNo") proposalNo: String
    ): Response<ProposalDataClassItem>

    @PUT("Proposal/{proposalNo}")
    suspend fun updateProposal(
        @Path("proposalNo") proposalNo: String,
        @Body proposal: ProposalDataClassItem
    ): Response<ProposalDataClassItem>

    @DELETE("Proposal/{proposalNo}")
    suspend fun deleteProposal(
        @Path("proposalNo") proposalNo: String
    ): Response<Void>
}