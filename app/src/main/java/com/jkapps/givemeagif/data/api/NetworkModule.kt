package com.jkapps.givemeagif.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://developerslife.ru/"

    private class JsonInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url()

            val url = originalUrl.newBuilder()
                .addQueryParameter("json", "true")
                .build()

            val request = originalRequest
                .newBuilder()
                .url(url)
                .build()
            return chain.proceed(request)
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(JsonInterceptor())
        .build()

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val gifService = retrofit.create(GifService::class.java)
}