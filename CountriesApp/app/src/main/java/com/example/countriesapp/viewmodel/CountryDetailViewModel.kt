package com.example.countriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesapp.model.Country
import com.example.countriesapp.service.CountryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()
    private var job: Job? = null

    fun getDataFromRoom(uuid : Int){
        job = CoroutineScope(Dispatchers.IO).launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val countryInfo = dao.getCountry(uuid)
            withContext(Dispatchers.Main){
                countryLiveData.value = countryInfo
            }
        }
    }
}