package com.example.countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesapp.model.Country
import com.example.countriesapp.service.CountriesApi
import com.example.countriesapp.service.CountryDatabase
import com.example.countriesapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CountryListViewModel(application: Application) : BaseViewModel(application) {


    private val countryApiService = CountriesApi()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L
    private var job: Job? = null

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {

        countryLoading.value = true

        disposable.add(
            countryApiService.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {

                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }


    private fun getDataFromSQLite() {
        countryLoading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            withContext(Dispatchers.Main){
                showCountries(countries)
                Toast.makeText(getApplication(), "Countries From SQLite", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun storeInSQLite(list: List<Country>) {

        job = CoroutineScope(Dispatchers.IO).launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray()) // -> list -> individual
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            withContext(Dispatchers.Main) {
                showCountries(list)
            }
        }
        customPreferences.saveTime(System.nanoTime())
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }
}