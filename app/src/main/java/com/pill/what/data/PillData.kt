package com.pill.what.data

import com.google.gson.annotations.SerializedName

data class PillData(
    @SerializedName("name")
    var name: String,
    @SerializedName("forms")
    var forms: String,
    @SerializedName("print_front")
    var print_front: String,
    @SerializedName("print_back")
    var print_back: String,
    @SerializedName("shapes")
    var shapes: String,
    @SerializedName("color1")
    var color1: String,
    @SerializedName("color2")
    var color2: String,
    @SerializedName("line_front")
    var line_front: String,
    @SerializedName("line_back")
    var line_back: String
)
