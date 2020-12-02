package com.bignerdranch.android.capstone2.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    var title: String = "",
    var id: String = "",
    @SerializedName("url_s") var url: String = "",
    @SerializedName("owner") var owner: String = "",
    @SerializedName("longitude") var longitude : String = "",
    @SerializedName("latitude")  var latitude : String = ""
) : Parcelable
