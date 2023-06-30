package com.christopher.mycharacterviewer.model


import com.google.gson.annotations.SerializedName

data class RelatedTopicModel(
    @SerializedName("FirstURL")
    val firstURL: String,
    @SerializedName("Icon")
    val icon: IconModel,
    @SerializedName("Result")
    val result: String,
    @SerializedName("Text")
    val text: String
)