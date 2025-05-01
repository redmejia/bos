package com.bitinovus.bos.di

import android.content.Context
import androidx.room.Room
import com.bitinovus.bos.BuildConfig
import com.bitinovus.bos.data.local.AppDatabase
import com.bitinovus.bos.data.local.DB_NAME
import com.bitinovus.bos.data.local.dao.TransactionDao
import com.bitinovus.bos.data.local.repository.TransactionRepositoryImpl
import com.bitinovus.bos.data.remote.api.BosApiService
import com.bitinovus.bos.data.remote.repository.BosApiRepositoryImpl
import com.bitinovus.bos.domain.repository.TransactionRepository
import com.bitinovus.bos.domain.usecases.time.Time
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideBosApi(): BosApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BosApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao = appDatabase.transactionDao()

    @Provides
    @Singleton
    fun provideTransactionRepositoryImpl(transactionDao: TransactionDao): TransactionRepository =
        TransactionRepositoryImpl(transactionDao)

    @Provides
    @Singleton
    fun provideBosApiRepositoryImp(api: BosApiService): BosApiRepositoryImpl =
        BosApiRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTime(): Time = Time()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()


}