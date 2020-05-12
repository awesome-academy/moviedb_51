package com.sunasterisk.moviedb_51.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.sunasterisk.moviedb_51.data.model.Movie

data class MoviesResponse(
    @SerializedName("page")
    val moviePage: Int,
    @SerializedName("total_results")
    val movieTotalResult: Int,
    @SerializedName("total_pages")
    val movieTotalPage: Int,
    @SerializedName("results")
    val movies: List<Movie>
)
