package com.aydemir.movieapp.data.remote

import com.aydemir.movieapp.core.Resource
import com.aydemir.movieapp.data.model.Movie
import com.aydemir.movieapp.data.model.ResponseMovieCast
import com.aydemir.movieapp.data.model.ResponseMovieDetail
import com.aydemir.movieapp.data.model.ResponseMovieList
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMovieDetail(id: Int): Flow<Resource<ResponseMovieDetail>>
    fun getMovieCast(id: Int): Flow<Resource<ResponseMovieCast>>

    fun getMovieSimilar(id: Int): Flow<Resource<ResponseMovieList>>
    fun getTopRated(): Flow<Resource<ResponseMovieList>>
    fun getPopular(): Flow<Resource<ResponseMovieList>>
    fun getUpcoming(): Flow<Resource<ResponseMovieList>>

    fun getFavoriteMovies(): Flow<Resource<List<Movie>>>
    fun insertFavoriteMovie(movie: Movie): Flow<Resource<Long>>
    fun deleteFavoriteMovie(movie: Movie): Flow<Resource<Unit>>
    fun getFavoriteMovie(id: Int): Flow<Resource<Movie?>>
}