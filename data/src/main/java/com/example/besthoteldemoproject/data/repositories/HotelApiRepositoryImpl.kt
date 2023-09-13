package com.example.besthoteldemoproject.data.repositories

import com.example.besthoteldemoproject.domain.repositories.HotelApiRepository
import com.example.besthoteldemoproject.retrofit.BookingResponse
import com.example.besthoteldemoproject.retrofit.HotelApiService
import com.example.besthoteldemoproject.retrofit.HotelResponse
import com.example.besthoteldemoproject.retrofit.RoomResponse
import retrofit2.Call

class HotelApiRepositoryImpl(private val api: HotelApiService): HotelApiRepository {
    override fun getHotelData(): Call<HotelResponse> {
        return api.getHotelData()
    }

    override fun getRoomData(): Call<RoomResponse> {
        return api.getRoomData()
    }

    override fun getBookingData(): Call<BookingResponse> {
        return api.getBookingData()
    }
}