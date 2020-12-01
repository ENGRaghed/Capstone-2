package com.bignerdranch.android.capstone2

import android.content.Context
import com.bignerdranch.android.capstone2.network.FlickrApi
import com.bignerdranch.android.capstone2.network.PhotoInterceptor
import com.bignerdranch.android.capstone2.repository.FlickrRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {

    private lateinit var app: App
    lateinit var retrofit: Retrofit
    private lateinit var flickrApi: FlickrApi

    fun init(app: App) {
        this.app = app
        initializeNetwork(app)
    }

    private fun getOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()
    }

    private fun initializeNetwork(context: Context) {
        retrofit = Retrofit.Builder()
            .baseUrl("https://www.flickr.com/")
            .client(getOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    val flickrRepository: FlickrRepository by lazy {
        FlickrRepository(flickrApi)
    }

}