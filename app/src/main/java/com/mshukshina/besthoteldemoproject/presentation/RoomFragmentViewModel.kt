package com.mshukshina.besthoteldemoproject.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshukshina.besthoteldemoproject.domain.repositories.HotelApiRepository
import com.mshukshina.besthoteldemoproject.retrofit.RoomResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomFragmentViewModel(private val repository: HotelApiRepository): ViewModel() {

    private val _roomDataResponse = MutableLiveData<RoomResponse?>(null)
    val roomDataResponse
        get() = _roomDataResponse

    init {
        getRoomData()
    }

    private fun getRoomData() {
        viewModelScope.launch {
            val call: Call<RoomResponse> = repository.getRoomData()
            call.enqueue(object : Callback<RoomResponse> {
                override fun onResponse(call: Call<RoomResponse>, response: Response<RoomResponse>) {
                    if(response.isSuccessful) {
                        if (response.body() == null) {
                            _roomDataResponse.postValue(null)
                            return
                        }
                        _roomDataResponse.postValue(response.body())
                    } else {
                        _roomDataResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}