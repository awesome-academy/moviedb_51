package com.sunasterisk.moviedb_51.data.model

import com.google.gson.annotations.SerializedName

data class Producer(
    @SerializedName("id")
    val produceID: Int,
    @SerializedName("logo_path")
    val produceLogo: String,
    @SerializedName("name")
    val produceName: String,
    @SerializedName("origin_country")
    val produceCountry: String
)
