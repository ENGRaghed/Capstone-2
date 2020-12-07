package com.bignerdranch.android.capstone2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.capstone2.ServiceLocator
import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.repository.FlickrRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PhotoViewModel : ViewModel(){

    var flickrRepository = ServiceLocator.flickrRepository


        fun fetchPhoto(lat : String,lon : String):LiveData<List<Photo>>{
            val photos = MutableLiveData<List<Photo>>()
            viewModelScope.launch{
                photos.value = flickrRepository.searchPhotos(lat, lon)
            }
            return photos
        }

    fun fetchPhotoWithRadius(lat : String,lon : String , radius : String): LiveData<List<Photo>> {
        val photos = MutableLiveData<List<Photo>>()
        viewModelScope.launch {
            photos.value =  flickrRepository.searchPhotos(lat,lon,radius)
        }
        return photos
    }
}