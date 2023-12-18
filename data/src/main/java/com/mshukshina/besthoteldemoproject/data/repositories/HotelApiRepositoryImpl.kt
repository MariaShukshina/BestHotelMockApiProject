package com.mshukshina.besthoteldemoproject.data.repositories

import com.mshukshina.besthoteldemoproject.domain.repositories.HotelApiRepository
import com.mshukshina.besthoteldemoproject.retrofit.BookingResponse
import com.mshukshina.besthoteldemoproject.retrofit.HotelApiService
import com.mshukshina.besthoteldemoproject.retrofit.HotelResponse
import com.mshukshina.besthoteldemoproject.retrofit.RoomResponse
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