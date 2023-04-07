package com.aydemir.core.extensions

import com.aydemir.core.base.ErrorResponse
import com.aydemir.core.base.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

fun <T> Flow<T>.applyDispatchers(): Flow<T> =
    this.flowOn(Dispatchers.IO)

fun <T> Flow<Resource<T>>.catchError(): Flow<Resource<T>> =
    this.catch {
        delay(100)
        emit(Resource.Error(ErrorResponse(statusMessage = it.message)))
    }