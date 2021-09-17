package com.lokarz.yelp.model.api.retrofit.yelp

import com.lokarz.yelp.model.api.retrofit.RetrofitConstants
import okhttp3.Interceptor
import okhttp3.Response

class YelpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()
        val apiKey =
            "8CKRFgOnjrrKgmacmnItITay3RKcIyx8cWjX0hr8nD4aCQUTukJOtXppbj1vSsJ_02g001xVcXZgOikTgg6eiucu1qZ5-OUBdwXdN_zDK4A0e2Im0LgUFTNBkPo6YXYx"

        requestBuilder.header(
            RetrofitConstants.AUTHORIZATION,
            "${RetrofitConstants.BEARER} $apiKey"
        )


        return chain.proceed(requestBuilder.build())
    }


}