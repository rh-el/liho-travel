package com.example.liho_mobile.di

import RetrofitClient
import TripRepository
import com.example.liho_mobile.data.api.ApiService
import com.example.liho_mobile.data.repositories.AccommodationRepository
import com.example.liho_mobile.data.repositories.UserRepository
import com.example.liho_mobile.ui.viewmodels.AccommodationViewModel
import com.example.liho_mobile.ui.viewmodels.TripViewModel
import com.example.liho_mobile.ui.viewmodels.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient.createService<ApiService>() }

    single { UserRepository(get()) }
    single { TripRepository(get())}
    single { AccommodationRepository(get())}

    viewModel { UserViewModel(get()) }
    viewModel { TripViewModel(get()) }
    viewModel { AccommodationViewModel(get()) }
}