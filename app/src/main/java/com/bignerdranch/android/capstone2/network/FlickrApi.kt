package com.bignerdranch.android.capstone2.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {


    @GET("services/rest?method=flickr.photos.search")
    suspend fun  searchByLocationPhotos(@Query("lat") lat: String, @Query("lon") lon: String): FlickrResponse


    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchByLocationPhotosWithDomain(@Query("lat") lat: String,
                                         @Query("lon") lon: String,
                                         @Query("radius") radius :String,@Query("radius_units") radius_units :String = "km"): FlickrResponse

}