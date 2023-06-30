package com.christopher.mycharacterviewer.model


import com.google.gson.annotations.SerializedName

data class IconModel(
    @SerializedName("Height")
    val height: String,
    @SerializedName("URL")
    val uRL: String,
    @SerializedName("Width")
    val width: String
)