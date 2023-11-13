package com.example.cryptoappwithkoin.repository

import com.example.cryptoappwithkoin.model.CryptoModel
import com.example.cryptoappwithkoin.service.CryptoAPI
import com.example.cryptoappwithkoin.util.Resource

class CryptoDownloadImpl(private val api:CryptoAPI) : CryptoDownload {

    override suspend fun downloadCryptos(): Resource<List<CryptoModel>> {
       return try {
           val response = api.getData()
           if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?:
                Resource.error("Error",null)
           }
           else{
               Resource.error("Error",null)
           }
       }
       catch (e : java.lang.Exception){
           Resource.error("No data",null)
       }

    }

}