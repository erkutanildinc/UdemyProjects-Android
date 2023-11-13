package com.example.cryptoappwithkoin.repository

import com.example.cryptoappwithkoin.model.CryptoModel
import com.example.cryptoappwithkoin.util.Resource

interface CryptoDownload {
    suspend fun downloadCryptos() : Resource<List<CryptoModel>>
}