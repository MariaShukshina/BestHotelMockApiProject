package com.mshukshina.besthoteldemoproject.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshukshina.besthoteldemoproject.domain.repositories.HotelApiRepository
import com.mshukshina.besthoteldemoproject.retrofit.BookingResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingFragmentViewModel(private val repository: HotelApiRepository): ViewModel() {

    private val _bookingResponse = MutableLiveData<BookingResponse?>()
    val bookingResponse
        get() = _bookingResponse

    init {
        getBookingData()
    }

    private fun getBookingData() {
        viewModelScope.launch {
            val call: Call<BookingResponse> = repository.getBookingData()
            call.enqueue(object : Callback<BookingResponse> {
                override fun onResponse(call: Call<BookingResponse>, response: Response<BookingResponse>) {
                    if (response.body() == null) {
                        _bookingResponse.postValue(null)
                        return
                    }
                    _bookingResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}