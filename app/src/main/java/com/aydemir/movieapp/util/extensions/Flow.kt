package com.aydemir.movieapp.util.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

fun <T> Flow<T>.applyDispatchers(): Flow<T> =
    this.flowOn(Dispatchers.IO)