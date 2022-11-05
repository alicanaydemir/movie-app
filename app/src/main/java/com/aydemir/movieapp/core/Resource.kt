package com.aydemir.movieapp.core

sealed class Resource<out T> {
    class Success<T>(val response: T) : Resource<T>()
    class Error(val errorResponse: ErrorResponse) : Resource<Nothing>()
    object ServiceUnavailable : Resource<Nothing>()
    object NoConnection : Resource<Nothing>()
    class Loading(val status: Boolean) : Resource<Nothing>()
}