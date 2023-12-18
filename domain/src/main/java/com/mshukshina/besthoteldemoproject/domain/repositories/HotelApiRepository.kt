package com.mshukshina.besthoteldemoproject.domain.repositories

import com.mshukshina.besthoteldemoproject.retrofit.BookingResponse
import com.mshukshina.besthoteldemoproject.retrofit.HotelResponse
import com.mshukshina.besthoteldemoproject.retrofit.RoomResponse
import retrofit2.Call

interface HotelApiRepository {
    fun getHotelData(): Call<HotelResponse>
    fun getRoomData(): Call<RoomResponse>
    fun getBookingData(): Call<BookingResponse>
}