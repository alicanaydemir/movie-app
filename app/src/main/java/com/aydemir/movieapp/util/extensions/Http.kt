package com.aydemir.movieapp.util.extensions

import com.aydemir.core.base.ErrorResponse
import com.aydemir.core.base.Resource
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Response

private val json = Json { ignoreUnknownKeys = true }

fun ResponseBody.convertErrorBody(): ErrorResponse {

    return json.decodeFromString<ErrorResponse>(this.string())
}

//fun Int.isCode401(): Boolean = this == 401

fun <M : Any> Response<M>.filterResponse(): Resource<M> {
    return when (this.isSuccessful) {
        true -> {
            this.body()?.let {
                Resource.Success(it)

            } ?: Resource.Error(ErrorResponse())
        }
        false -> {

            try {
                val error = this.errorBody()?.convertErrorBody()
                Resource.Error(error!!)

            } catch (e: Exception) {
                Resource.Error(ErrorResponse())
            }
        }
    }
}
