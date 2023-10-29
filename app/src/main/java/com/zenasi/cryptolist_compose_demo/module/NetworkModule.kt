package com.zenasi.cryptolist_compose_demo.module

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    // api 所需的 module
    @Singleton
    @Provides
    fun provideHttpLog(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("HttpLog debug: ", it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideApiServices(retrofit: Retrofit): AppApiModule =
        retrofit.create(AppApiModule::class.java)

    @Singleton
    @Provides
    fun provideGsonConvert(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideMoshiConvert(): MoshiConverterFactory = MoshiConverterFactory.create()


    @Named("API")
    @Singleton
    @Provides
    fun provideAPIOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
//            .retryOnConnectionFailure(true)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()


    @Named("Socket")
    @Singleton
    @Provides
    fun provideWebSocketOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .pingInterval(30, TimeUnit.SECONDS)
        .build()


    // ref https://docs.coincap.io/
    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        moshiConverterFactory: MoshiConverterFactory,
        @Named("API") okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.coincap.io")
        .addConverterFactory(gsonConverterFactory)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()
}