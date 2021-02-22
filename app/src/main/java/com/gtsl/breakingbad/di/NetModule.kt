package com.gtsl.breakingbad.di

import com.gtsl.breakingbad.net.repository.ApiService
import com.gtsl.breakingbad.net.repository.BreakingBadRepo
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val ReadTimeoutMs: Long = 10000
val WriteTimeoutMs: Long = 10000
val ConnectionTimeoutMs: Long = 10000

val netModule: Module = module {

    single {
        provideOkHttpClient()
    }
    single {
        provideApiService(get())
    }

    single {
        provideRepository(get())
    }
}

private fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .readTimeout(ReadTimeoutMs, TimeUnit.MILLISECONDS)
        .writeTimeout(WriteTimeoutMs, TimeUnit.MILLISECONDS)
        .connectTimeout(ConnectionTimeoutMs, TimeUnit.MILLISECONDS)
        .build()


private fun provideApiService(client: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl("https://breakingbadapi.com/api/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ApiService::class.java)

private fun provideRepository(apiService: ApiService) = BreakingBadRepo(apiService)

