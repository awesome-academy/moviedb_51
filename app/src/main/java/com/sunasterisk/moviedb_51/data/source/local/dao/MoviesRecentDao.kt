package com.sunasterisk.moviedb_51.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.sunasterisk.moviedb_51.data.model.MovieRecent
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface MoviesRecentDao {
    @Query("DELETE FROM tbl_movie")
    fun deleteAllMoviesLocal()

    @Insert(onConflict = REPLACE)
    fun addMovieLocal(vararg movie: MovieRecent)

    @Query("SELECT * FROM tbl_movie")
    fun getAllMoviesLocal(): Flowable<List<MovieRecent>>

    @Query("SELECT count(*) FROM tbl_movie where id = :movieId")
    fun searchMoveLocal(movieId: Int): Maybe<Int>
}
