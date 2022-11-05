package com.aydemir.movieapp.ui.favorites

import androidx.lifecycle.viewModelScope
import com.aydemir.movieapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
) : BaseViewModel() {

    init {
        viewModelScope.launch {

        }
    }

}