package com.lokarz.yelp.model.api.retrofit.yelp

import com.lokarz.yelp.pojo.yelp.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.pojo.yelp.search.SearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface IYelpService {



    @GET("/v3/businesses/{id}")
    fun getBusinessDetails(@Path("id") id: String): Single<BusinessDetailResponse>

    @GET("/v3/businesses/search")
    fun searchBusiness(@QueryMap map : Map<String, String>): Single<SearchResponse>

}