package com.example.countriesapp.service

import com.example.countriesapp.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesApi {

    private val Base_URL = "https://raw.githubusercontent.com/"
    private val api =
        Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(CountriesAPIService::class.java)

    fun getData() : Single<MutableList<Country>>{
        return api.getCountries()
    }
}