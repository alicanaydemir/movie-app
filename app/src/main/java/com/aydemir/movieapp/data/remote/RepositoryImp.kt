package com.aydemir.movieapp.data.remote

import com.aydemir.movieapp.core.Resource
import com.aydemir.movieapp.data.model.ResponseMovieDetail
import com.aydemir.movieapp.data.model.ResponseMovieDetailImages
import com.aydemir.movieapp.util.extensions.applyDispatchers
import com.aydemir.movieapp.util.extensions.filterResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: MovieAppApi
) : Repository {

    override fun getMovieDetail(): Flow<Resource<ResponseMovieDetail>> =
        flow {
            delay(500)
            val data = api.getMovieDetail()
            emit(data.filterResponse())
        }.onStart { emit(Resource.Loading(true)) }
            .onCompletion { emit(Resource.Loading(false)) }
            .applyDispatchers()

    override fun getMovieDetailImages(): Flow<Resource<ResponseMovieDetailImages>> =
        flow {
            delay(500)
            val data = api.getMovieDetailImages()
            emit(data.filterResponse())
        }.onStart { emit(Resource.Loading(true)) }
            .onCompletion { emit(Resource.Loading(false)) }
            .applyDispatchers()
}