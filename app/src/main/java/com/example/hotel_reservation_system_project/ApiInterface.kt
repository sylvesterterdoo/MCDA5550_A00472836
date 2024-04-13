package com.example.hotel_reservation_system_project

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    @GET("/hotels")
    fun getHotelsLists(): Call<List<HotelListData>>


    @POST("/api/reservation")
    fun makeReservation(@Body request: HotelReservationRequest): Call<HotelReservationResponse>

}