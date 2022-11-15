package com.aydemir.movieapp.data.remote

import com.aydemir.movieapp.core.Resource
import com.aydemir.movieapp.data.model.ResponseMovieCast
import com.aydemir.movieapp.data.model.ResponseMovieDetail
import com.aydemir.movieapp.data.model.ResponseMovieList
import com.aydemir.movieapp.util.extensions.applyDispatchers
import com.aydemir.movieapp.util.extensions.filterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: MovieAppApi
) : Repository {

    override fun getMovieDetail(id: Int): Flow<Resource<ResponseMovieDetail>> = flow {
        val data = api.getMovieDetail(id)
        emit(data.filterResponse())
    }.applyDispatchers()

    override fun getMovieCast(id: Int): Flow<Resource<ResponseMovieCast>> = flow {
        val data = api.getMovieCast(id)
        emit(data.filterResponse())
    }.applyDispatchers()

    override fun getMovieRecommendations(id: Int): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getMovieRecommendations(id)
        emit(data.filterResponse())
    }.applyDispatchers()

    override fun getTopRated(): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getTopRated()
        emit(data.filterResponse())
    }.applyDispatchers()

    override fun getPopular(): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getPopular()
        emit(data.filterResponse())
    }.applyDispatchers()

    override fun getUpcoming(): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getUpcoming()
        emit(data.filterResponse())
    }.applyDispatchers()
}