package com.example.besthoteldemoproject.data.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface HotelApiService {

    @GET("35e0d18e-2521-4f1b-a575-f0fe366f66e3")
    fun getHotelData(): Call<HotelResponse>

    @GET("f9a38183-6f95-43aa-853a-9c83cbb05ecd")
    fun getRoomData(): Call<RoomResponse>

    @GET("e8868481-743f-4eb2-a0d7-2bc4012275c8")
    fun getBookingData(): Call<BookingResponse>

}