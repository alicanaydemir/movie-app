package com.aydemir.movieapp.data.remote

import com.aydemir.movieapp.data.model.ResponseMovieCast
import com.aydemir.movieapp.data.model.ResponseMovieDetail
import com.aydemir.movieapp.data.model.ResponseMovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAppApi {
    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Int): Response<ResponseMovieDetail>

    @GET("movie/{id}/similar")
    suspend fun getMovieSimilar(@Path("id") id: Int): Response<ResponseMovieList>

    @GET("movie/{id}/credits")
    suspend fun getMovieCast(@Path("id") id: Int): Response<ResponseMovieCast>

    @GET("movie/top_rated")
    suspend fun getTopRated(): Response<ResponseMovieList>

    @GET("movie/popular")
    suspend fun getPopular(): Response<ResponseMovieList>

    @GET("movie/upcoming")
    suspend fun getUpcoming(): Response<ResponseMovieList>
}
