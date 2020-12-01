package com.bignerdranch.android.capstone2.network

import com.bignerdranch.android.capstone2.model.Photo
import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var photos: List<Photo>
}
