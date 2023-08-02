package com.example.cryptoapp.service

import com.example.cryptoapp.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoService {


    @GET("/atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getCoins() : Call<MutableList<CryptoModel>>
}