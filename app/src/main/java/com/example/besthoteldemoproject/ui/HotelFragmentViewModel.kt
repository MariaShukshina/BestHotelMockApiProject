package com.example.besthoteldemoproject.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.besthoteldemoproject.data.retrofit.HotelResponse
import com.example.besthoteldemoproject.domain.repositories.HotelApiRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelFragmentViewModel(private val repository: HotelApiRepository): ViewModel() {

    private val _hotelDataResponse = MutableLiveData<HotelResponse?>()
    val hotelDataResponse
        get() = _hotelDataResponse

    init {
        getHotelData()
    }

    private fun getHotelData() {
        viewModelScope.launch {
            val call: Call<HotelResponse> = repository.getHotelData()
            call.enqueue(object : Callback<HotelResponse> {
                override fun onResponse(call: Call<HotelResponse>, response: Response<HotelResponse>) {
                    if (response.body() == null) {
                        _hotelDataResponse.postValue(null)
                        return
                    }
                    _hotelDataResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<HotelResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}