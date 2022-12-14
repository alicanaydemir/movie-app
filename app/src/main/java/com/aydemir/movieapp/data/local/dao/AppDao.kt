package com.aydemir.movieapp.data.local.dao

import androidx.room.*
import com.aydemir.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: Movie): Long

    @Delete
    suspend fun deleteFavoriteMovie(movie: Movie)

    @Query("SELECT * FROM table_movies where id=:id")
    suspend fun getFavoriteMovie(id: Int): Movie?

    @Query("SELECT * FROM table_movies")
    fun getFavoriteMovies(): Flow<List<Movie>>
}