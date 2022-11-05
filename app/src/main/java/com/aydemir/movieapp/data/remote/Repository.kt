package com.aydemir.movieapp.data.remote

import com.aydemir.movieapp.core.Resource
import com.aydemir.movieapp.data.model.ResponseMovieDetail
import com.aydemir.movieapp.data.model.ResponseMovieDetailImages
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMovieDetail(): Flow<Resource<ResponseMovieDetail>>
    fun getMovieDetailImages(): Flow<Resource<ResponseMovieDetailImages>>
}