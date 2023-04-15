package com.aydemir.core.database.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aydemir.core.database.local.Converters
import com.aydemir.core.database.local.dao.AppDao
import com.aydemir.core.database.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}