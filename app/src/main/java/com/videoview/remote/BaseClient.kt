package com.videoview.remote

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object NetworkDefaults {

    fun getJsonAdapterFactory() = KotlinJsonAdapterFactory()

    fun getMoshi(factory: JsonAdapter.Factory): Moshi = Moshi.Builder().add(factory).build()

    fun getConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi).asLenient()

    //   fun getForceJsonInterceptor() = JsonResponseInterceptor()

    fun getOkHttpClient(forTest: Boolean = false): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (forTest) {
            builder.connectTimeout(1, TimeUnit.MINUTES)
            builder.readTimeout(1, TimeUnit.MINUTES)
        }

        // interceptors.forEach { builder.addInterceptor(it) }

        return builder.build()
    }
}
