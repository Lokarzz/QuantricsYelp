package com.lokarz.yelp.model.repository.yelp

import com.lokarz.yelp.pojo.yelp.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.pojo.yelp.search.SearchResponse
import io.reactivex.rxjava3.core.Single

class YelpRepository constructor(private val yelpRemoteRepository: YelpRemoteRepository) {

    fun searchBusiness(map: Map<String, String>): Single<SearchResponse> {
        return yelpRemoteRepository.searchBusiness(map)
    }

    fun getBusinessDetails(id: String): Single<BusinessDetailResponse> {
        return yelpRemoteRepository.getBusinessDetails(id)
    }
}