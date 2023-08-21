package com.example.composecryptoapp.service

import com.example.composecryptoapp.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoService {

    @GET("/atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getCoins() : Response<MutableList<CryptoModel>>
}