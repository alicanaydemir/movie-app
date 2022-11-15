package com.aydemir.movieapp.util

import androidx.room.TypeConverter
import com.aydemir.movieapp.data.model.Movie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringToMovieList(value: String): List<Movie> {

        return json.decodeFromString<List<Movie>>(value)
    }

    @TypeConverter
    fun fromMovieListToString(list: List<Movie?>?): String {

        return json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringToGenreList(value: String): List<Int> {

        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromGenreListToString(list: List<Int?>?): String {

        return json.encodeToString(list)
    }
}