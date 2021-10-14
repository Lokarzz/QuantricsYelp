package com.lokarz.yelp.model.repository

import com.lokarz.yelp.model.repository.poko.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.model.repository.poko.search.SearchResponse
import io.reactivex.rxjava3.core.Single

interface IYelpRepository {

    fun onSearchBusiness(map: Map<String, String>): Single<SearchResponse>

    fun onBusinessDetails(id: String): Single<BusinessDetailResponse>

}