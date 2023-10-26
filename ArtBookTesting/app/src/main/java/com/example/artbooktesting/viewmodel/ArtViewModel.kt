package com.example.artbooktesting.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artbooktesting.model.ImageResponse
import com.example.artbooktesting.repo.ArtRepository
import com.example.artbooktesting.repo.ArtRepositoryInterface
import com.example.artbooktesting.roomdb.Art
import com.example.artbooktesting.util.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class ArtViewModel @ViewModelInject constructor(private val repository: ArtRepositoryInterface) : ViewModel() {

    //Art Fragment

    val artList = repository.getArt()



    //ImageApı Fragment

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageURL : LiveData<String>
        get() = selectedImage




    //Art Details Fragment

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }


    fun makeArt(name : String,artistName : String,year : String){
        if(name.isEmpty()||artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter Name, artist, year",null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e : Exception){
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        val art = Art(name,artistName,yearInt,selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun setSelectedImage(url : String){
        selectedImage.value = url
    }

    fun deleteArt(art : Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art : Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun searchForImage(searchString : String){
        if(searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }

}