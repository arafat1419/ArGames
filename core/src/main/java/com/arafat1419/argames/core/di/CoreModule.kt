package com.arafat1419.argames.core.di

import androidx.room.Room
import com.arafat1419.argames.core.BuildConfig
import com.arafat1419.argames.core.data.local.LocalDataSource
import com.arafat1419.argames.core.data.local.db.AppDatabase
import com.arafat1419.argames.core.domain.repository.IPagingRepository
import com.arafat1419.argames.core.data.network.api.ApiService
import com.arafat1419.argames.core.data.network.datasource.NetworkDataSource
import com.arafat1419.argames.core.data.network.repository.DataRepository
import com.arafat1419.argames.core.data.network.repository.PagingRepository
import com.arafat1419.argames.core.domain.repository.IDataRepository
import com.arafat1419.argames.core.domain.usecase.DataInteractor
import com.arafat1419.argames.core.domain.usecase.DataUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory { get<AppDatabase>().appDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            BuildConfig.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    single { NetworkDataSource(get()) }
    single { LocalDataSource(get()) }
    single<IPagingRepository> {
        PagingRepository(get())
    }
    single<IDataRepository> {
        DataRepository(get(), get())
    }
}

val useCaseModule = module {
    factory<DataUseCase> { DataInteractor(get(), get()) }
}