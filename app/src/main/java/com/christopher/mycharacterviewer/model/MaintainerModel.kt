package com.christopher.mycharacterviewer.model


import com.google.gson.annotations.SerializedName

data class MaintainerModel(
    @SerializedName("github")
    val github: String
)