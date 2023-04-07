package com.aydemir.movieapp.data.remote

import com.aydemir.core.base.Resource
import com.aydemir.movieapp.data.local.dao.AppDao
import com.aydemir.movieapp.data.model.Movie
import com.aydemir.movieapp.data.model.ResponseMovieCast
import com.aydemir.movieapp.data.model.ResponseMovieDetail
import com.aydemir.movieapp.data.model.ResponseMovieList
import com.aydemir.movieapp.util.extensions.applyDispatchers
import com.aydemir.movieapp.util.extensions.catchError
import com.aydemir.movieapp.util.extensions.filterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: MovieAppApi,
    private val appDao: AppDao
) : Repository {

    override fun getMovieDetail(id: Int): Flow<Resource<ResponseMovieDetail>> = flow {
        val data = api.getMovieDetail(id)
        emit(data.filterResponse())
    }.applyDispatchers().catchError()

    override fun getMovieCast(id: Int): Flow<Resource<ResponseMovieCast>> = flow {
        val data = api.getMovieCast(id)
        emit(data.filterResponse())
    }.applyDispatchers().catchError()

    override fun getMovieRecommendations(id: Int): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getMovieRecommendations(id)
        emit(data.filterResponse())
    }.applyDispatchers().catchError()

    override fun getTopRated(): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getTopRated()
        emit(data.filterResponse())
    }.applyDispatchers().catchError()

    override fun getPopular(): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getPopular()
        emit(data.filterResponse())
    }.applyDispatchers().catchError()

    override fun getUpcoming(): Flow<Resource<ResponseMovieList>> = flow {
        val data = api.getUpcoming()
        emit(data.filterResponse())
    }.applyDispatchers().catchError()

    override fun getFavoriteMovies(): Flow<Resource<List<Movie>>> = flow {
        appDao.getFavoriteMovies().collect {
            emit(Resource.Success(it))
        }
    }.applyDispatchers().catchError()

    override fun insertFavoriteMovie(movie: Movie): Flow<Resource<Long>> = flow {
        emit(Resource.Success(appDao.insertFavoriteMovie(movie)))
    }.applyDispatchers().catchError()

    override fun deleteFavoriteMovie(movie: Movie): Flow<Resource<Unit>> = flow {
        emit(Resource.Success(appDao.deleteFavoriteMovie(movie)))
    }.applyDispatchers().catchError()

    override fun getFavoriteMovie(id: Int): Flow<Resource<Movie?>> = flow {
        emit(Resource.Success(appDao.getFavoriteMovie(id)))
    }.applyDispatchers().catchError()
}