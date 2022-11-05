package com.aydemir.movieapp.data.remote

import com.aydemir.movieapp.data.model.ResponseMovieDetail
import com.aydemir.movieapp.data.model.ResponseMovieDetailImages
import retrofit2.Response
import retrofit2.http.GET

interface MovieAppApi {
    @GET("movie/985939")
    suspend fun getMovieDetail(): Response<ResponseMovieDetail>

    @GET("movie/985939/images")
    suspend fun getMovieDetailImages(): Response<ResponseMovieDetailImages>
}
