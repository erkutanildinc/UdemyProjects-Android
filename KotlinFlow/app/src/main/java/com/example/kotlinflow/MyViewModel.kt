package com.example.kotlinflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    val countDownTimerFLow = flow<Int>{
        val countDownFrom = 10
        var counter = countDownFrom
        emit(countDownFrom)
        while (counter>0){
            delay(1000)
            counter--
            emit(counter)
        }
    }

    init {
        collectInViewModel()
    }

    private fun collectInViewModel(){

        viewModelScope.launch {
            countDownTimerFLow.filter {
                it%3 == 0
            }.map {
                it+it
            }.collect{
                println(it)
            }
        }

    }

    private val _liveData = MutableLiveData<String>("Kotlin Live Data")
    val liveData : LiveData<String> = _liveData

    fun changeLiveData(){
        _liveData.value = "Live Data"
    }

    private val _stateFlow = MutableStateFlow<String>("Kotlin State Flow")
    val stateFlow : StateFlow<String> = _stateFlow.asStateFlow()

    fun changeStateFlow(){
        _stateFlow.value = "State Flow"
    }

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun changeSharedFlow(){
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }
}