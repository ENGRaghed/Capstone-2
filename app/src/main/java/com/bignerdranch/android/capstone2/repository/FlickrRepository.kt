package com.bignerdranch.android.capstone2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.network.FlickrApi
import com.bignerdranch.android.capstone2.network.FlickrResponse
import com.bignerdranch.android.capstone2.network.PhotoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "FlickrFetchr"

class FlickrRepository ( private val flickrApi: FlickrApi){


    suspend fun searchPhotoRequest(lat: String,lon : String, radius : String): FlickrResponse {
        return flickrApi.searchByLocationPhotosWithDomain(lat, lon,radius)
    }
    suspend fun searchPhotos(lat: String,lon : String,radius: String): List<Photo> {
       return flickrApi.searchByLocationPhotosWithDomain(lat, lon, radius).photos.photos
    }



    suspend fun searchPhotoRequest(lat: String,lon : String): FlickrResponse {
        return flickrApi.searchByLocationPhotos(lat, lon)
    }
    suspend fun searchPhotos(lat: String,lon : String): List<Photo>{
        return  flickrApi.searchByLocationPhotos(lat,lon).photos.photos
//        return fetchPhotoMetadata(searchPhotoRequest(lat,lon))
    }

//    private fun fetchPhotoMetadata(flickrRequest: FlickrResponse)
//            : LiveData<List<Photo>> {
//        val responseLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
//        responseLiveData.value = flickrRequest.photos.photos
//
////        flickrRequest.enqueue(object : Callback<FlickrResponse> {
////            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
////                Log.e(TAG, "Failed to fetch photos", t)
////            }
////            override fun onResponse(
////                call: Call<FlickrResponse>,
////                response: Response<FlickrResponse>
////            ) {
////                Log.d(TAG, "Response received")
////                val flickrResponse: FlickrResponse? = response.body()
////                val photoResponse: PhotoResponse? = flickrResponse?.photos
////                var photos: List<Photo> = photoResponse?.photos
////                    ?: mutableListOf()
////                photos = photos.filterNot {
////                    it.url.isBlank()
////                }
////                responseLiveData.value = photos
////            }
////        })
////        return responseLiveData
//        return responseLiveData
//    }
}