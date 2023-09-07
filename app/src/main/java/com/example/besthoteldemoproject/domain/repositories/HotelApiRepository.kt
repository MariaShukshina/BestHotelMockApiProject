package com.example.besthoteldemoproject.domain.repositories

import com.example.besthoteldemoproject.data.retrofit.BookingResponse
import com.example.besthoteldemoproject.data.retrofit.HotelResponse
import com.example.besthoteldemoproject.data.retrofit.RoomResponse
import retrofit2.Call

interface HotelApiRepository {
    fun getHotelData(): Call<HotelResponse>
    fun getRoomData(): Call<RoomResponse>
    fun getBookingData(): Call<BookingResponse>
}