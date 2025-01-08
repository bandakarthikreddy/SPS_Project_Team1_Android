package com.singlepointsol.carinsurance.retrofitinstance

import com.singlepointsol.carinsurance.api.CustomerQueryAPI
import com.singlepointsol.carinsurance.constants.Token.AUTH_TOKEN
import com.singlepointsol.carinsurance.constants.URLS.QUERY_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CustomerQueryRetrofitInstance {

    private fun getInstance() : Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${AUTH_TOKEN}")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(QUERY_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val customerQueryAPI : CustomerQueryAPI = getInstance().create(CustomerQueryAPI::class.java)
}