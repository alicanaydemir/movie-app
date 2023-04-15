package com.aydemir.core.database.local

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringToGenreList(value: String?): List<Int>? {

        return value?.let { json.decodeFromString(it) }
    }

    @TypeConverter
    fun fromGenreListToString(list: List<Int?>?): String {

        return json.encodeToString(list)
    }
}