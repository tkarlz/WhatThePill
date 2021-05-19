package com.pill.what.data

import com.google.gson.annotations.SerializedName

data class PillInfo(
    @SerializedName("name")
    var name: String,
    @SerializedName("code")
    var code: String,
    @SerializedName("image")
    var image: String,
    @SerializedName("ingredient")
    var ingredient: String,
    @SerializedName("company")
    var company: String,
    @SerializedName("seq")
    var seq: String
)
