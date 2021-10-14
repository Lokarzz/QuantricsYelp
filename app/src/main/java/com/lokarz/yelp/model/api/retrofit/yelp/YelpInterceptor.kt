package com.lokarz.yelp.model.api.retrofit.yelp

import com.lokarz.yelp.model.api.retrofit.RetrofitConstants
import okhttp3.Interceptor
import okhttp3.Response

class YelpInterceptor(private val yelpApiKey : String) : Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()


        requestBuilder.header(
            RetrofitConstants.AUTHORIZATION,
            "${RetrofitConstants.BEARER} $yelpApiKey"
        )


        return chain.proceed(requestBuilder.build())
    }


}