package com.example.hotel_reservation_system_project

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    val BASE_URL = "http://demo-env.eba-v3cghqbc.us-east-1.elasticbeanstalk.com/"
    fun getClient(): ApiInterface {
        val retrofit = Retrofit.Builder()
            // Set the Root URL
//            .baseUrl("http://10.0.2.2:8080/")
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson converter
            .build()

        val api = retrofit.create(ApiInterface::class.java)
        return api
    }
}