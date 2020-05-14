package com.sunasterisk.moviedb_51.data.model

import com.google.gson.annotations.SerializedName


data class MovieTrailers(
    @SerializedName("results")
    val moviesTrailers: List<MovieTrailerAttribute>
)

data class MovieTrailerAttribute(
    @SerializedName("id")
    val movieTrailerID: String,
    @SerializedName("key")
    val movieTrailerKey: String,
    @SerializedName("name")
    val movieTrailerName: String
)
