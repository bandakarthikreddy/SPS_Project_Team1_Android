package com.singlepointsol.carinsurance.retrofitinstance

import com.singlepointsol.carinsurance.api.VehicleAPI
import com.singlepointsol.carinsurance.constants.Token.AUTH_TOKEN
import com.singlepointsol.carinsurance.constants.URLS.VEHICLE_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VehicleRetrofitInstance {

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
            .baseUrl(VEHICLE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val vehicleAPI : VehicleAPI = getInstance().create(VehicleAPI::class.java)
}