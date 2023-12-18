package com.mshukshina.besthoteldemoproject.di

import com.mshukshina.besthoteldemoproject.data.repositories.HotelApiRepositoryImpl
import com.mshukshina.besthoteldemoproject.domain.repositories.HotelApiRepository
import com.mshukshina.besthoteldemoproject.presentation.BookingFragmentViewModel
import com.mshukshina.besthoteldemoproject.presentation.HotelFragmentViewModel
import com.mshukshina.besthoteldemoproject.presentation.RoomFragmentViewModel
import com.mshukshina.besthoteldemoproject.retrofit.HotelApiService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://0ca1e6fb-5a66-4817-8e31-0f39eb8e3e87.mock.pstmn.io/"

val appModule = module {

    viewModel { HotelFragmentViewModel(get()) }

    viewModel { RoomFragmentViewModel(get()) }

    viewModel { BookingFragmentViewModel(get()) }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(HotelApiService::class.java)
    }

    single<HotelApiRepository> {
        HotelApiRepositoryImpl(get())
    }

}