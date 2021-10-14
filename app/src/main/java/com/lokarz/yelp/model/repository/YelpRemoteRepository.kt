package com.lokarz.yelp.model.repository

import com.lokarz.yelp.model.api.retrofit.yelp.IYelpService
import com.lokarz.yelp.model.repository.poko.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.model.repository.poko.search.SearchResponse
import io.reactivex.rxjava3.core.Single

class YelpRemoteRepository (private val iYelpService: IYelpService) : IYelpRepository {

    override fun onSearchBusiness(map: Map<String, String>): Single<SearchResponse> {
        return iYelpService.searchBusiness(map)

    }

    override fun onBusinessDetails(id: String): Single<BusinessDetailResponse> {
        return iYelpService.getBusinessDetails(id)
    }
}