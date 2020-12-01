package com.bignerdranch.android.capstone2.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

//    @GET("services/rest?method=flickr.photos.search")
//    fun fetchPhotos(): Response<List<PhotoDto>>

//    @GET("services/rest?method=flickr.photos.search")
//    fun searchPhotos(@Query("text") query: String): Call<FlickrResponse>

    @GET("services/rest?method=flickr.photos.search")
    fun searchByLocationPhotos(@Query("lat") lat: String, @Query("lon") lon: String): Call<FlickrResponse>

}