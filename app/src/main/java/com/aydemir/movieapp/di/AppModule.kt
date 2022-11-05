package com.aydemir.movieapp.di

import android.content.Context
import android.content.SharedPreferences
import com.aydemir.movieapp.BuildConfig
import com.aydemir.movieapp.data.remote.MovieAppApi
import com.aydemir.movieapp.data.remote.Repository
import com.aydemir.movieapp.data.remote.RepositoryImp
import com.aydemir.movieapp.util.helper.SharedHelper
import com.aydemir.movieapp.util.service.TokenInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTokenInterceptor(
        pref: SharedHelper,
        @ApplicationContext context: Context
    ): TokenInterceptor {
        return TokenInterceptor(pref, context)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideSharedHelper(sharedPreferences: SharedPreferences): SharedHelper {
        return SharedHelper(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideMovieAppApi(
        tokenInterceptor: TokenInterceptor,
        factory: Converter.Factory
    ): MovieAppApi {
        val okClient = OkHttpClient.Builder()
            .addNetworkInterceptor(tokenInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(factory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(MovieAppApi::class.java)
    }

    @Singleton
    @Provides
    fun provideJsonConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: MovieAppApi
    ): Repository = RepositoryImp(api)
}