package com.sunasterisk.moviedb_51.utils

enum class Types(val title: String, val data: String) {
    CATEGORIES("CATEGORIES", "categories"),
    GENRES("GENRES", "genres"),
    POPULAR("POPULAR", "popular"),
    TOP_RATED("TOP RATED", "top_rated"),
    UPCOMING("UPCOMING", "upcoming"),
    NOW_PLAYING("NOW PLAYING", "now_playing")
}
