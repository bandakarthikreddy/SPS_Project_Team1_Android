package com.singlepointsol.carinsurance.api

import com.singlepointsol.carinsurance.dataclass.AgentDataClassItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AgentAPI {
    @GET("Agent")
    suspend fun getAgent() : Response<List<AgentDataClassItem>>

    @POST("Agent/{agentID}")
    suspend fun addAgent(
        @Path("agentID") agentID: String,
        @Body agent: AgentDataClassItem
    ) : Response<AgentDataClassItem>

    @GET("Agent/{agentID}")
    suspend fun getAgentByAgentID(
        @Path("agentID") agentID : String
    ) : Response<AgentDataClassItem>

    @PUT("Agent/{agentID}")
    suspend fun updateAgent(
        @Path("agentID") agentID: String,
        @Body agent : AgentDataClassItem
    ) : Response<AgentDataClassItem>

    @DELETE("Agent/{agentID}")
    suspend fun deleteAgent(
        @Path("agentID") agentID: String
    ) : Response<Void>
}