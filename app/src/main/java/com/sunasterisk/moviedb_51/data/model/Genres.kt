package com.sunasterisk.moviedb_51.data.model

import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("id")
    val genresID: Int,
    @SerializedName("name")
    val genresName: String
)
