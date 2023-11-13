package com.example.cryptoappwithkoin.di

import com.example.cryptoappwithkoin.repository.CryptoDownload
import com.example.cryptoappwithkoin.repository.CryptoDownloadImpl
import com.example.cryptoappwithkoin.service.CryptoAPI
import com.example.cryptoappwithkoin.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    //singleton scope
    single {
        val BASE_URL = "https://raw.githubusercontent.com/"
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(
            CryptoAPI::class.java)
    }

    single<CryptoDownload> {
        //since we defined retrofit above, this repository will asks for retrofit and we can simply
        //say get() in order to inject it even here
        CryptoDownloadImpl(get())
    }

    viewModel{
        CryptoViewModel(get())
    }
}