package com.christopher.mycharacterviewer.model


import com.google.gson.annotations.SerializedName

data class MetaModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("dev_milestone")
    val devMilestone: String,
    @SerializedName("developer")
    val developer: List<DeveloperModel>,
    @SerializedName("example_query")
    val exampleQuery: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("js_callback_name")
    val jsCallbackName: String,
    @SerializedName("maintainer")
    val maintainer: MaintainerModel,
    @SerializedName("name")
    val name: String,
    @SerializedName("perl_module")
    val perlModule: String,
    @SerializedName("production_state")
    val productionState: String,
    @SerializedName("repo")
    val repo: String,
    @SerializedName("signal_from")
    val signalFrom: String,
    @SerializedName("src_domain")
    val srcDomain: String,
    @SerializedName("src_id")
    val srcId: Int,
    @SerializedName("src_name")
    val srcName: String,
    @SerializedName("src_options")
    val srcOptions: SrcOptionsModel,
    @SerializedName("status")
    val status: String,
    @SerializedName("tab")
    val tab: String,
    @SerializedName("topic")
    val topic: List<String>,
    @SerializedName("unsafe")
    val unsafe: Int
)