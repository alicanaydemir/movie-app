package com.aydemir.movieapp.ui.movieDetail

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.aydemir.core.R
import com.aydemir.core.base.BaseViewModel
import com.aydemir.core.base.ErrorResponse
import com.aydemir.core.base.Resource
import com.aydemir.movieapp.data.model.Cast
import com.aydemir.movieapp.data.model.Genre
import com.aydemir.movieapp.data.model.Movie
import com.aydemir.movieapp.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    @ApplicationContext val application: Context,
    private val repositoryImp: Repository
) : BaseViewModel() {

    var id: Int = 0

    private val _uiStateMovieDetail =
        MutableStateFlow<UiStateMovieDetail>(UiStateMovieDetail.Loading)
    val uiStateMovieDetail: StateFlow<UiStateMovieDetail> = _uiStateMovieDetail

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        viewModelScope.launch {
            delay(500)
            getDetails()
            isFavoriteMovie()
        }
    }

    private suspend fun getDetails() {
        repositoryImp.getMovieDetail(id)
            .zip(repositoryImp.getMovieRecommendations(id)) { r1, r2 ->
                if (r1 is Resource.Success && r2 is Resource.Success) {
                    val movieDetail = MovieDetail()
                    r1.response.apply {
                        movieDetail.id = id
                        movieDetail.title = title
                        movieDetail.overview = overview
                        movieDetail.releaseDate = releaseDate
                        movieDetail.pathPoster = posterPath
                        movieDetail.pathBackDrops = backdropPath ?: posterPath
                        movieDetail.genres = genres
                    }
                    r2.response.apply {
                        movieDetail.movieRecommendations = results
                    }
                    movieDetail
                } else {
                    null
                }
            }.zip(repositoryImp.getMovieCast(id)) { movieDetail, r3 ->
                if (movieDetail != null && r3 is Resource.Success) {
                    movieDetail.cast = r3.response.cast
                    movieDetail
                } else null
            }.collect { result ->
                if (result == null) {
                    _uiStateMovieDetail.value =
                        UiStateMovieDetail.Error(
                            ErrorResponse(
                                statusMessage = application.resources.getString(
                                    R.string.error_occurred
                                )
                            )
                        )
                } else {
                    _uiStateMovieDetail.value = UiStateMovieDetail.Success(result)
                }
            }
    }

    fun insertFavoriteMovie() {
        if (_uiStateMovieDetail.value is UiStateMovieDetail.Success) {
            (_uiStateMovieDetail.value as UiStateMovieDetail.Success).let { movie ->
                viewModelScope.launch {
                    repositoryImp.insertFavoriteMovie(
                        Movie(
                            id = movie.data.id,
                            title = movie.data.title,
                            posterPath = movie.data.pathPoster
                        )
                    ).collect {
                        when (it) {
                            is Resource.Success -> {
                                isFavoriteMovie()
                            }
                            is Resource.Error -> {}
                            Resource.NoConnection -> {}
                        }

                    }
                }
            }
        }
    }


    fun deleteFavoriteMovie() {
        if (_uiStateMovieDetail.value is UiStateMovieDetail.Success) {
            (_uiStateMovieDetail.value as UiStateMovieDetail.Success).let { movie ->
                viewModelScope.launch {
                    repositoryImp.deleteFavoriteMovie(
                        Movie(
                            id = movie.data.id,
                            title = movie.data.title,
                            posterPath = movie.data.pathPoster
                        )
                    ).collect {
                        when (it) {
                            is Resource.Success -> {
                                isFavoriteMovie()
                            }
                            is Resource.Error -> {}
                            Resource.NoConnection -> {}
                        }

                    }
                }
            }
        }
    }

    fun doFavorite() {
        if (_isFavorite.value) {
            deleteFavoriteMovie()
        } else {
            insertFavoriteMovie()
        }
    }

    private fun isFavoriteMovie() {
        viewModelScope.launch {
            repositoryImp.getFavoriteMovie(
                id
            ).collect {
                when (it) {
                    is Resource.Success -> {
                        _isFavorite.value = it.response != null
                    }
                    is Resource.Error -> {}
                    Resource.NoConnection -> {}
                }

            }
        }
    }

}

sealed class UiStateMovieDetail {
    data class Success(val data: MovieDetail) : UiStateMovieDetail()
    data class Error(val response: ErrorResponse) : UiStateMovieDetail()
    object Loading : UiStateMovieDetail()
}

class MovieDetail {
    var id: Int? = null
    var overview: String? = null
    var title: String? = null
    var pathPoster: String? = null
    var pathBackDrops: String? = null
    var releaseDate: String? = null
    var cast: List<Cast?>? = null
    var genres: List<Genre?>? = null
    var movieRecommendations: List<Movie?>? = null
}