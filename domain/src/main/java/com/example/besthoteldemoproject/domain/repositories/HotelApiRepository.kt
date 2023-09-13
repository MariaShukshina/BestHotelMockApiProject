package com.example.besthoteldemoproject.domain.repositories

import com.example.besthoteldemoproject.retrofit.BookingResponse
import com.example.besthoteldemoproject.retrofit.HotelResponse
import com.example.besthoteldemoproject.retrofit.RoomResponse
import retrofit2.Call

interface HotelApiRepository {
    fun getHotelData(): Call<HotelResponse>
    fun getRoomData(): Call<RoomResponse>
    fun getBookingData(): Call<BookingResponse>
}