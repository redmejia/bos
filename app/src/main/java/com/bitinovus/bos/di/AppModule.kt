package com.bitinovus.bos.di

import com.bitinovus.bos.BuildConfig
import com.bitinovus.bos.data.remote.api.BosApi
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.domain.usecases.time.Time
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    // Logger
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Bos API
    @Provides
    @Singleton
    fun provideBosApi(): BosApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBosApiRepositoryImp(api: BosApi): BosApiRepositoryImpl = BosApiRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTime(): Time = Time()


}