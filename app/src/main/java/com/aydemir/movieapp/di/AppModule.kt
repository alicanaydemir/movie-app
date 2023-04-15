package com.aydemir.movieapp.di

import android.content.Context
import android.content.SharedPreferences
import com.aydemir.core.helper.SharedHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideSharedHelper(sharedPreferences: SharedPreferences): SharedHelper {
        return SharedHelper(sharedPreferences)
    }

}