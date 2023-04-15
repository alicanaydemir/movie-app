package com.aydemir.core.data.di

import android.content.Context
import com.aydemir.core.BuildConfig
import com.aydemir.core.data.MovieAppApi
import com.aydemir.core.data.Repository
import com.aydemir.core.data.RepositoryImp
import com.aydemir.core.data.TokenInterceptor
import com.aydemir.core.database.local.dao.AppDao
import com.aydemir.core.helper.SharedHelper
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideTokenInterceptor(
        pref: SharedHelper,
        @ApplicationContext context: Context
    ): TokenInterceptor {
        return TokenInterceptor(pref, context)
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
    fun provideRepository(
        api: MovieAppApi,
        appDao: AppDao
    ): Repository = RepositoryImp(api,appDao)
}