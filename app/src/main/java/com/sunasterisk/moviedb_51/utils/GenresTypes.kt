package com.sunasterisk.moviedb_51.utils

import com.sunasterisk.moviedb_51.R

enum class GenresTypes(val genreId: Int, val genreNameId: Int, val genreImageResource: Int) {
    ACTION(28, R.string.title_action, R.drawable.ic_action),
    ADVENTURE(12, R.string.title_adventure, R.drawable.ic_adventure),
    ANIMATION(16, R.string.title_animation, R.drawable.ic_animation),
    COMEDY(35, R.string.title_comedy, R.drawable.ic_comedy),
    CRIME(80, R.string.title_crime, R.drawable.ic_crime),
    DOCUMENTARY(99, R.string.title_documentary, R.drawable.ic_action),
    DRAMA(18, R.string.title_drama, R.drawable.ic_drama),
    FAMILY(10751, R.string.title_family, R.drawable.ic_family),
    FANTASY(14, R.string.title_fantasy, R.drawable.ic_fantasy),
    HISTORY(36, R.string.title_history, R.drawable.ic_history),
    HORROR(27, R.string.title_horror, R.drawable.ic_horror),
    MUSIC(10402, R.string.title_music, R.drawable.ic_music),
    MYSTERY(9648, R.string.title_mystery, R.drawable.ic_mystery),
    ROMANCE(10749, R.string.title_romance, R.drawable.ic_romance),
    SCIENCE_FICTION(878, R.string.title_science_fiction, R.drawable.ic_science_fiction),
    TV_MOVIE(10770, R.string.title_tv_movie, R.drawable.ic_tv_movie),
    THRILLER(53, R.string.title_thriller, R.drawable.ic_thriller),
    WAR(10752, R.string.title_war, R.drawable.ic_war),
    WESTERN(37, R.string.title_western, R.drawable.ic_western),
    NONE(0, R.string.title_none, 0),
}
