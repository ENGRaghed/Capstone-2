package com.bignerdranch.android.capstone2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.capstone2.ServiceLocator
import com.bignerdranch.android.capstone2.model.Photo
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

//    fun fetchPhoto(lat : String,lon : String) : LiveData<List<Photo>> {
//        val photos = MutableLiveData<LiveData<List<Photo>>>()
//            viewModelScope.launch(Dispatchers.IO) {
//                val retu = flickrRepository.searchPhotos(lat,lon)
//                photos.postValue(retu)
//        }
//       return photos.value
//    }
    /*
    LiveData can be used to get value from ViewModel to Fragment.

Make the function findbyID return LiveData and observe it in the fragment.

Function in ViewModel

fun findbyID(id: Int): LiveData</*your data type*/> {
    val result = MutableLiveData</*your data type*/>()
    viewModelScope.launch {
       val returnedrepo = repo.delete(id)
       result.postValue(returnedrepo)
    }
    return result
}
     */
//
//
//    fun fetchPhoto(lat : String,lon : String) : LiveData<List<Photo>>{
//    viewModelScope.launch {
//        return flickrRepository.searchPhotos(lat, lon)
//    }
//}



}