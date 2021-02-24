package com.lokarz.kotlinbaseapp.dagger.module.retrofit

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named


@Module
class RetrofitModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl() : String{
        return "https://www.google.com"
    }

    @Provides
    fun providesClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()

        builder.connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)


        return builder.build()
    }

    @Provides
    fun provideRetrofit(@Named("baseUrl") baseUrl: String, okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    fun provideService(retrofit: Retrofit) : IAppService {
        return retrofit.create(IAppService::class.java)
    }

}