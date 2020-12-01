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
    fun searchPhotoRequest(lat: String,lon : String): Call<FlickrResponse> {
        return flickrApi.searchByLocationPhotos(lat, lon)
    }
    fun searchPhotos(lat: String,lon : String): LiveData<List<Photo>> {
        return fetchPhotoMetadata(searchPhotoRequest(lat,lon))
    }

    private fun fetchPhotoMetadata(flickrRequest: Call<FlickrResponse>)
            : LiveData<List<Photo>> {
        val responseLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
        flickrRequest.enqueue(object : Callback<FlickrResponse> {
            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }
            override fun onResponse(
                call: Call<FlickrResponse>,
                response: Response<FlickrResponse>
            ) {
                Log.d(TAG, "Response received")
                val flickrResponse: FlickrResponse? = response.body()
                val photoResponse: PhotoResponse? = flickrResponse?.photos
                var photos: List<Photo> = photoResponse?.photos
                    ?: mutableListOf()
                photos = photos.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = photos
            }
        })
        return responseLiveData
    }
}