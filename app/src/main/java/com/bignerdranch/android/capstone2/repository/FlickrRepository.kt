package com.bignerdranch.android.capstone2.repository

import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.network.FlickrApi

private const val TAG = "FlickrFetchr"

class FlickrRepository ( private val flickrApi: FlickrApi ){

    suspend fun searchPhotos(lat: String,lon : String,radius: String): List<Photo> {
       return flickrApi.searchByLocationPhotosWithDomain(lat, lon, radius).photos.photos
    }
    suspend fun searchPhotos(lat: String,lon : String): List<Photo>{
        return  flickrApi.searchByLocationPhotos(lat,lon).photos.photos
    }

}