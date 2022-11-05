package com.aydemir.movieapp.ui.movieDetail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.aydemir.movieapp.core.BaseViewModel
import com.aydemir.movieapp.core.ErrorResponse
import com.aydemir.movieapp.core.Resource
import com.aydemir.movieapp.data.model.ResponseMovieDetailImages
import com.aydemir.movieapp.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repositoryImp: Repository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading(true))
    val uiState: StateFlow<UiState> = _uiState

    private val _imgBackDrops = MutableStateFlow<ResponseMovieDetailImages?>(null)
    val imgBackDrops: StateFlow<ResponseMovieDetailImages?> = _imgBackDrops


    init {
        viewModelScope.launch {
            repositoryImp.getMovieDetail().catch {
                Log.i("catch", it.message.toString())
            }.collect {
                when (it) {
                    is Resource.Error -> {
                        _uiState.value = UiState.Error(it.errorResponse)
                    }
                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading(it.status)
                    }
                    is Resource.NoConnection -> {}
                    is Resource.ServiceUnavailable -> {}
                    is Resource.Success -> {}
                }
            }

            repositoryImp.getMovieDetailImages().catch {
                Log.i("catch", it.message.toString())
            }.collect {
                when (it) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.NoConnection -> {}
                    is Resource.ServiceUnavailable -> {}
                    is Resource.Success -> {
                        _imgBackDrops.value = it.response
                    }
                }
            }
        }
    }

}

sealed class UiState {
    data class Success(val news: String) : UiState()
    data class Error(val response: ErrorResponse) : UiState()
    data class Loading(val status: Boolean) : UiState()
}