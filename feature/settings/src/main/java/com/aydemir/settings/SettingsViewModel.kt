package com.aydemir.settings

import androidx.lifecycle.viewModelScope
import com.aydemir.core.base.BaseViewModel
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