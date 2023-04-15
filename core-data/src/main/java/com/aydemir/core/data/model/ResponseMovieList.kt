package com.aydemir.core.data.model

import android.os.Parcelable
import com.aydemir.core.database.model.Movie
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ResponseMovieList(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<Movie?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
) : Parcelable