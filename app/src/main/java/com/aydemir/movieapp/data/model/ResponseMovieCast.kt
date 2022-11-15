package com.aydemir.movieapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ResponseMovieCast(
    @SerialName("cast")
    val cast: List<Cast?>?,
    @SerialName("id")
    val id: Int?
) : Parcelable

@Serializable
@Parcelize
data class Cast(
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("cast_id")
    val castId: Int?,
    @SerialName("character")
    val character: String?,
    @SerialName("credit_id")
    val creditId: String?,
    @SerialName("gender")
    val gender: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("known_for_department")
    val knownForDepartment: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("order")
    val order: Int?,
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("profile_path")
    val profilePath: String?
) : Parcelable