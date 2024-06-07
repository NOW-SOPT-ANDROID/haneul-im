package com.sopt.now

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.compose.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {
    private const val BASE_URL: String = BuildConfig.AUTH_BASE_URL

    //
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    //
    val retrofit: Retrofit by lazy {
        val json = Json {
            ignoreUnknownKeys = true
        }
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }


    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}

object ServicePool {
    val authService = ApiFactory.create<AuthService>()
}