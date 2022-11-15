package com.aydemir.movieapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aydemir.movieapp.data.local.dao.AppDao
import com.aydemir.movieapp.data.model.Movie
import com.aydemir.movieapp.util.Converters

@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}