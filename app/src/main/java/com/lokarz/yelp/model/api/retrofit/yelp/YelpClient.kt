package com.lokarz.yelp.model.api.retrofit.yelp

import com.lokarz.yelp.model.api.retrofit.BaseClient
import okhttp3.Interceptor

class YelpClient(private val interceptor: YelpInterceptor) : BaseClient<IYelpService>(url, IYelpService::class.java) {

    companion object {
        const val url = "https://api.yelp.com"
    }

    override fun clientHeaderInterceptor(): Interceptor {
        println("clientHeaderInterceptor: $interceptor")
        return interceptor
    }
}