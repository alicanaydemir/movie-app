package com.aydemir.core.data

import com.aydemir.core.base.Resource
import com.aydemir.core.data.model.ResponseMovieCast
import com.aydemir.core.data.model.ResponseMovieDetail
import com.aydemir.core.data.model.ResponseMovieList
import com.aydemir.core.database.model.Movie
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMovieDetail(id: Int): Flow<Resource<ResponseMovieDetail>>
    fun getMovieCast(id: Int): Flow<Resource<ResponseMovieCast>>

    fun getMovieRecommendations(id: Int): Flow<Resource<ResponseMovieList>>
    fun getTopRated(): Flow<Resource<ResponseMovieList>>
    fun getPopular(): Flow<Resource<ResponseMovieList>>
    fun getUpcoming(): Flow<Resource<ResponseMovieList>>

    fun getFavoriteMovies(): Flow<Resource<List<Movie>>>
    fun insertFavoriteMovie(movie: Movie): Flow<Resource<Long>>
    fun deleteFavoriteMovie(movie: Movie): Flow<Resource<Unit>>
    fun getFavoriteMovie(id: Int): Flow<Resource<Movie?>>
}