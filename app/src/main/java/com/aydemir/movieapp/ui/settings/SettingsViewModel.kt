package com.aydemir.movieapp.ui.settings

import androidx.lifecycle.viewModelScope
import com.aydemir.movieapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
) : BaseViewModel() {

    init {
        viewModelScope.launch {

        }
    }

}