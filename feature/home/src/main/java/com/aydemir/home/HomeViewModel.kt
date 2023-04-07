package com.aydemir.home

import androidx.lifecycle.viewModelScope
import com.aydemir.core.base.BaseViewModel
import com.aydemir.core.base.ErrorResponse
import com.aydemir.core.base.Resource
import com.aydemir.core.data.model.Movie
import com.aydemir.core.data.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryImp: Repository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiStateHome>(UiStateHome.Loading)
    val uiState: StateFlow<UiStateHome> = _uiState

    init {
        viewModelScope.launch {
            repositoryImp.getUpcoming()
                .zip(repositoryImp.getPopular()) { r1, r2 ->
                    if (r1 is Resource.Success && r2 is Resource.Success) {
                        val homeList = HomeList()
                        homeList.listHomeItem?.add(HomeItem(true, "Upcoming"))
                        homeList.listHomeItem?.add(HomeItem(data = r1.response.results))
                        homeList.listHomeItem?.add(HomeItem(true, "Popular"))
                        homeList.listHomeItem?.add(HomeItem(data = r2.response.results))
                        homeList
                    } else {
                        null
                    }
                }.zip(repositoryImp.getTopRated()) { homeList, r3 ->
                    if (homeList != null && r3 is Resource.Success) {
                        homeList.listHomeItem?.add(HomeItem(true, "Top Rated"))
                        homeList.listHomeItem?.add(HomeItem(data = r3.response.results))
                        homeList
                    } else null
                }.collect { result ->
                    if (result == null) {
                        _uiState.value = UiStateHome.Error(ErrorResponse())
                    } else {
                        _uiState.value = UiStateHome.Success(result)
                    }
                }
        }
    }

}

sealed class UiStateHome {
    data class Success(val data: HomeList) : UiStateHome()
    data class Error(val response: ErrorResponse) : UiStateHome()
    object Loading : UiStateHome()
    object NoConnection : UiStateHome()
}

class HomeList {
    var listHomeItem: MutableList<HomeItem?>? = mutableListOf()
}

data class HomeItem(
    val isHeader: Boolean = false,
    val txtHeader: String = "",
    var data: List<Movie?>? = null
)