package com.example.besthoteldemoproject.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface HotelApiService {

    @GET("get?test=665")
    fun getHotelData(): Call<HotelResponse>

    @GET("get?test=670")
    fun getRoomData(): Call<RoomResponse>

    @GET("get?test=700")
    fun getBookingData(): Call<BookingResponse>

}