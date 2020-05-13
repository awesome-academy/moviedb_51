package com.sunasterisk.moviedb_51.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_movie")
data class MovieRecent(
    @ColumnInfo(name = "id")
    @PrimaryKey
    @SerializedName("id")
    val movieID: Int,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val movieTitle: String,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val moviePosterPath: String
)
