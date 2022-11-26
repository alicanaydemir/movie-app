package com.aydemir.movieapp.ui.favorites

import androidx.lifecycle.viewModelScope
import com.aydemir.movieapp.core.BaseViewModel
import com.aydemir.movieapp.core.Resource
import com.aydemir.movieapp.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repositoryImp: Repository
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            repositoryImp.getFavorites().collect {
                when (it) {
                    is Resource.Success -> {

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