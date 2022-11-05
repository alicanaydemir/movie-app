package com.aydemir.movieapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ResponseMovieDetailImages(
    @SerialName("backdrops")
    val backdrops: List<Backdrop?>?,
    @SerialName("id")
    val id: Int?
) : Parcelable

@Serializable
@Parcelize
data class Backdrop(
    @SerialName("aspect_ratio")
    val aspectRatio: Double?,
    @SerialName("file_path")
    val filePath: String?
) : Parcelable