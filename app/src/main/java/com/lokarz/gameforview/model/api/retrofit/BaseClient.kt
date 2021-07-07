package com.lokarz.gameforview.model.api.retrofit

import com.google.gson.GsonBuilder
import com.lokarz.gameforview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseClient<T>( baseUrl: String, classService: Class<T>) {
    var service: T? = null

    init {
        val mRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        service = mRetrofit.create(classService)
    }

    private val okHttpClient: OkHttpClient
        get() {
            val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()

            builder.connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(httpLoggingInterceptor)
            }
            return builder.build()
        }

    private val gsonConverterFactory: GsonConverterFactory
        get() {
            val gson = GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .setLenient()
                .create()
            return GsonConverterFactory.create(gson)
        }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    }
}