package com.bignerdranch.android.capstone2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.capstone2.ServiceLocator
import com.bignerdranch.android.capstone2.model.Photo

class PhotoViewModel : ViewModel(){

    var flickrRepository = ServiceLocator.flickrRepository

    fun fetchPhoto(lat : String,lon : String): LiveData<List<Photo>> {
        return flickrRepository.searchPhotos(lat,lon)
    }
}