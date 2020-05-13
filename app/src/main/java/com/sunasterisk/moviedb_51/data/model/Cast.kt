package com.sunasterisk.moviedb_51.data.model

import com.google.gson.annotations.SerializedName

data class Casts(
    @SerializedName("cast")
    val casts: List<CastAttribute>
)

data class CastAttribute(
    @SerializedName("id")
    val castId: Int,
    @SerializedName("name")
    val castName: String,
    @SerializedName("profile_path")
    val castProfilePath: String
)
