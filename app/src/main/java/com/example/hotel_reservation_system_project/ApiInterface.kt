package com.example.hotel_reservation_system_project

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/app/hotels")
    fun getHotelsLists(): Call<List<HotelListData>>
}