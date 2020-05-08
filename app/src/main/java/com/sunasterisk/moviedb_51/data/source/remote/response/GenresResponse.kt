package com.sunasterisk.moviedb_51.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.sunasterisk.moviedb_51.data.model.Genres

data class GenresResponse(@SerializedName("genres") var genres: List<Genres>)
