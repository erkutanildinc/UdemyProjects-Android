package com.example.countriesapp.service

import com.example.countriesapp.model.Country
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface CountriesAPIService {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries() : Single<List<Country>>
}