package com.example.cryptoappwithkoin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoappwithkoin.model.CryptoModel
import com.example.cryptoappwithkoin.repository.CryptoDownload
import com.example.cryptoappwithkoin.service.CryptoAPI
import com.example.cryptoappwithkoin.util.Resource
import com.example.cryptoappwithkoin.view.RecyclerViewAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoViewModel(private val cryptoDownloadRepository: CryptoDownload) : ViewModel() {

    val cryptoList = MutableLiveData<Resource<List<CryptoModel>>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value = Resource.error(throwable.localizedMessage ?: "error",true)
    }

    fun getDataFromApi(){

        cryptoLoading.value = Resource.loading(data = true)

        /*val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(
            CryptoAPI::class.java)
            */

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val resource = cryptoDownloadRepository.downloadCryptos()

            withContext(Dispatchers.Main){
                resource.data?.let {
                    cryptoList.value =  resource
                    cryptoLoading.value = Resource.loading(data = false)
                    cryptoError.value = Resource.error("",data=false)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}