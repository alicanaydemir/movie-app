package com.aydemir.movieapp.di

import android.app.Application
import androidx.room.Room
import com.aydemir.movieapp.data.local.database.MovieAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room
        .databaseBuilder(app, MovieAppDatabase::class.java, "movie_app_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideAppDao(db: MovieAppDatabase) = db.appDao()
}