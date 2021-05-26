package com.pill.what.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class APIResultData(
    @SerializedName("formulation")
    var form: String,
    @SerializedName("dividing line")
    var line: String?,
    @SerializedName("shape")
    var shape: String?,
    @SerializedName("color")
    var colors: ArrayList<String?>,
    @SerializedName("character")
    var prints: ArrayList<String>,
    @SerializedName("base64img")
    var base64img: String
) : Parcelable