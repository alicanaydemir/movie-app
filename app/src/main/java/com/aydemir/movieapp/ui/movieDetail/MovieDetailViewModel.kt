package com.aydemir.movieapp.ui.movieDetail

import androidx.lifecycle.viewModelScope
import com.aydemir.movieapp.core.BaseViewModel
import com.aydemir.movieapp.core.ErrorResponse
import com.aydemir.movieapp.core.Resource
import com.aydemir.movieapp.data.model.Cast
import com.aydemir.movieapp.data.model.Genre
import com.aydemir.movieapp.data.model.Movie
import com.aydemir.movieapp.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repositoryImp: Repository
) : BaseViewModel() {

    var id: Int = 0

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            delay(500)
            repositoryImp.getMovieDetail(id)
                .zip(repositoryImp.getMovieRecommendations(id)) { r1, r2 ->
                    if (r1 is Resource.Success && r2 is Resource.Success) {
                        val movieDetail = MovieDetail()
                        r1.response.apply {
                            movieDetail.id = id
                            movieDetail.title = title
                            movieDetail.overview = overview
                            movieDetail.pathPoster = posterPath
                            movieDetail.pathBackDrops = backdropPath
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
                    result?.apply {
                        _uiState.value = UiState.Success(result)
                    }
                }
        }
    }

}

sealed class UiState {
    data class Success(val data: MovieDetail) : UiState()
    data class Error(val response: ErrorResponse) : UiState()
    object Loading : UiState()
}

class MovieDetail {
    var id: Int? = null
    var overview: String? = null
    var title: String? = null
    var pathPoster: String? = null
    var pathBackDrops: String? = null
    var cast: List<Cast?>? = null
    var genres: List<Genre?>? = null
    var movieRecommendations: List<Movie?>? = null
}