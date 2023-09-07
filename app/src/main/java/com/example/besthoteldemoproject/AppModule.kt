package com.example.besthoteldemoproject

import com.example.besthoteldemoproject.data.repositories.HotelApiRepositoryImpl
import com.example.besthoteldemoproject.data.retrofit.HotelApiService
import com.example.besthoteldemoproject.domain.repositories.HotelApiRepository
import com.example.besthoteldemoproject.ui.HotelFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://run.mocky.io/v3/"

val appModule = module {

    viewModel { HotelFragmentViewModel(get()) }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(HotelApiService::class.java)
    }

    single<HotelApiRepository> { HotelApiRepositoryImpl(get()) }

}