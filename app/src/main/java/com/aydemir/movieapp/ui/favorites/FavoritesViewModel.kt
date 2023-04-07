package com.aydemir.movieapp.ui.favorites

import androidx.lifecycle.viewModelScope
import com.aydemir.core.base.BaseViewModel
import com.aydemir.core.base.ErrorResponse
import com.aydemir.core.base.Resource
import com.aydemir.core.data.model.Movie
import com.aydemir.core.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repositoryImp: Repository
) : BaseViewModel() {

    private val _uiStateFavorites = MutableStateFlow<UiStateFavorites>(UiStateFavorites.Loading)
    val uiStateFavorites: StateFlow<UiStateFavorites> = _uiStateFavorites

    init {
        viewModelScope.launch {
            repositoryImp.getFavoriteMovies().collect {
                when (it) {
                    is Resource.Success -> {
                        if(it.response.isEmpty().not()){
                            _uiStateFavorites.value = UiStateFavorites.Success(it.response)
                        }else{
                            _uiStateFavorites.value = UiStateFavorites.Empty
                        }
                    }
                    is Resource.Error -> {

                    }
                    is Resource.NoConnection -> {

                    }
                }
            }
        }
    }

}

sealed class UiStateFavorites {
    data class Success(val data: List<Movie>) : UiStateFavorites()
    data class Error(val response: ErrorResponse) : UiStateFavorites()
    object Empty : UiStateFavorites()
    object Loading : UiStateFavorites()
    object NoConnection : UiStateFavorites()
}
