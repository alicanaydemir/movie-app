package com.aydemir.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aydemir.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(data: List<Movie>)

    @Query("SELECT * FROM table_movies")
    fun getFavorites(): Flow<List<Movie>>
}