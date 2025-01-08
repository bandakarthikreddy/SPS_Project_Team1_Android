package com.singlepointsol.carinsurance.retrofitinstance

import com.singlepointsol.carinsurance.api.ProposalAPI
import com.singlepointsol.carinsurance.constants.Token.AUTH_TOKEN
import com.singlepointsol.carinsurance.constants.URLS.PROPOSAL_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProposalRetrofitInstance {
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
            .baseUrl(PROPOSAL_BASE_URL) // Set the base URL
            .client(client)   // Attach OkHttpClient with interceptor
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter
            .build()
    }

    val proposalAPI: ProposalAPI = getInstance().create(ProposalAPI::class.java)
}