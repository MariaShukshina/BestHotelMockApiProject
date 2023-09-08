package com.example.besthoteldemoproject

import com.example.besthoteldemoproject.data.repositories.HotelApiRepositoryImpl
import com.example.besthoteldemoproject.data.retrofit.HotelApiService
import com.example.besthoteldemoproject.domain.repositories.HotelApiRepository
import com.example.besthoteldemoproject.ui.HotelFragmentViewModel
import com.example.besthoteldemoproject.ui.RoomFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://run.mocky.io/v3/"

private const val INVALID_MOCK_URL_HEAD = "https://www.google.com/search?q=%D0%BD%D0%BE%D0%BC%D0%B5%D1%80+%D0%BB%D1%8E%D0%BA%D1%81+%D0%B2+%D0%BE%D1%82%D0%B5%D0%BB%D0%B8+%D0%B5%D0%B3%D0%B8%D0%BF%D1%82%D0%B0+%D1%81+%D1%81%D0%BE%D0%B1%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D1%8B%D0%BC+%D0%B1%D0%B0%D1%81%D1%81%D0%B5%D0%B9%D0%BD%D0%BE%D0%BC&tbm=isch&ved=2ahUKEwilufKp-4KBAxUfJxAIHR4uAToQ2-cCegQIABAA&oq=%D0%BD%D0%BE%D0%BC%D0%B5%D1%80+%D0%BB%D1%8E%D0%BA%D1%81+%D0%B2+%D0%BE%D1%82%D0%B5%D0%BB%D0%B8+%D0%B5%D0%B3%D0%B8%D0%BF%D1%82%D0%B0+%D1%81+%D1%81%D0%BE%D0%B1%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D1%8B%D0%BC+%D0%B1%D0%B0%D1%81%D1%81%D0%B5%D0%B9%D0%BD%D0%BE%D0%BC&gs_lcp=CgNpbWcQAzoECCMQJ1CqAVi6HGDmHWgAcAB4AIABXIgB3wySAQIyNZgBAKABAaoBC2d3cy13aXotaW1nwAEB&sclient=img&ei=Y3fuZOX7KJ_OwPAPntyE0AM&bih=815&biw=1440"

private const val INVALID_MOCK_URL_TAIL = "#imgrc=Nr2wzh3vuY4jEM&imgdii=zTCXWbFgrQ5HBM"

const val INVALID_MOCK_URL = INVALID_MOCK_URL_HEAD + INVALID_MOCK_URL_TAIL

const val VALID_URL = "https://m0.th.by/wp-content/uploads/2019/01/PREMIER-LE-REVE-HOTEL-SPA-5.jpg"

val appModule = module {

    viewModel { HotelFragmentViewModel(get()) }

    viewModel { RoomFragmentViewModel(get()) }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(HotelApiService::class.java)
    }

    single<HotelApiRepository> { HotelApiRepositoryImpl(get()) }

}