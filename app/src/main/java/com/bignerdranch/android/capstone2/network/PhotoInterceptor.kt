package com.bignerdranch.android.capstone2.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


private const val API_KEY = "320a9aca3f0ecf54f6fd08c6a0dec645"
class PhotoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newUrl: HttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key",
                API_KEY
            )
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
//            .addQueryParameter("extras","geo")
            .addQueryParameter("extras", "geo,url_s")
            .addQueryParameter("safesearch", "1")
//            .addQueryParameter("has_geo","1")
            .build()
        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}