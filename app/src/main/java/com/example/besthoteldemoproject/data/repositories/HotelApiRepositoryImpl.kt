package com.example.besthoteldemoproject.data.repositories

import com.example.besthoteldemoproject.data.retrofit.BookingResponse
import com.example.besthoteldemoproject.data.retrofit.HotelApiService
import com.example.besthoteldemoproject.data.retrofit.HotelResponse
import com.example.besthoteldemoproject.data.retrofit.RoomResponse
import com.example.besthoteldemoproject.domain.repositories.HotelApiRepository
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