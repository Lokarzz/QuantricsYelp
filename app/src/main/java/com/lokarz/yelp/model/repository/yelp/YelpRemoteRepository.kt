package com.lokarz.yelp.model.repository.yelp

import com.lokarz.yelp.model.api.retrofit.yelp.IYelpService
import com.lokarz.yelp.pojo.yelp.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.pojo.yelp.search.SearchResponse
import io.reactivex.rxjava3.core.Single

class YelpRemoteRepository constructor(private val iYelpService: IYelpService) {

    fun searchBusiness(map: Map<String, String>): Single<SearchResponse> {
        return iYelpService.searchBusiness(map)
    }

    fun getBusinessDetails(id: String): Single<BusinessDetailResponse> {
        return iYelpService.getBusinessDetails(id)
    }
}