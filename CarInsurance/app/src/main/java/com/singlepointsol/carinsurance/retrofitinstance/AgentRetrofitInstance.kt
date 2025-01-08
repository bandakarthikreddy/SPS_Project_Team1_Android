package com.singlepointsol.carinsurance.retrofitinstance

import com.singlepointsol.carinsurance.api.AgentAPI
import com.singlepointsol.carinsurance.constants.Token.AUTH_TOKEN
import com.singlepointsol.carinsurance.constants.URLS.AGENT_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AgentRetrofitInstance {
    private fun getInstance(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${AUTH_TOKEN}") // Add token to the header
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(AGENT_BASE_URL) // Set the base URL
            .client(client)   // Attach OkHttpClient with interceptor
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter
            .build()
    }

    val agentAPI : AgentAPI = getInstance().create(AgentAPI::class.java)
}