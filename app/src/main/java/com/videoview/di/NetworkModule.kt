package com.videoview.di


import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.videoview.BuildConfig
import com.videoview.remote.NetworkDefaults
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideJsonAdapterFactory(): JsonAdapter.Factory = NetworkDefaults.getJsonAdapterFactory()

    @Provides
    @Singleton
    fun provideMoshi(factory: JsonAdapter.Factory): Moshi = NetworkDefaults.getMoshi(factory)

    @Provides
    @Singleton
    fun provideConverterFactory(moshi: Moshi): Converter.Factory = NetworkDefaults.getConverterFactory(moshi)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return NetworkDefaults.getOkHttpClient()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

}