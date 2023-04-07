package com.aydemir.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aydemir.core.data.local.Converters
import com.aydemir.core.data.local.dao.AppDao
import com.aydemir.core.data.model.Movie

@Database(
    entities = [Movie::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}