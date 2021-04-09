package com.tiagomdosantos.networkutils.lib.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkUtilsModule = module {
    single { setupOkHttpClient() }
}

fun setupOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient
        .Builder()
        .connectTimeout(timeout = 60L, unit = TimeUnit.SECONDS)
        .readTimeout(timeout = 60L, unit = TimeUnit.SECONDS)
        .addInterceptor(interceptor = httpLoggingInterceptor)
        .build()
}

fun setupRetrofit(
    okHttpClient: OkHttpClient,
    url: String
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
