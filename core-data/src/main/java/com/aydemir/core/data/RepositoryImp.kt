package com.aydemir.core.data

import com.aydemir.core.base.Resource
import com.aydemir.core.data.model.ResponseMovieCast
import com.aydemir.core.data.model.ResponseMovieDetail
import com.aydemir.core.data.model.ResponseMovieList
import com.aydemir.core.database.local.dao.AppDao
import com.aydemir.core.database.model.Movie
import com.aydemir.core.extensions.applyDispatchers
import com.aydemir.core.extensions.catchError
import com.aydemir.core.extensions.filterResponse
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