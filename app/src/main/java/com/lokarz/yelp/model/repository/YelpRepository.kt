package com.lokarz.yelp.model.repository

import com.lokarz.yelp.model.repository.poko.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.model.repository.poko.search.SearchResponse
import io.reactivex.rxjava3.core.Single

class YelpRepository constructor(
    private val yelpRemoteRepository: YelpRemoteRepository,
    private val yelpLocalRepository: YelpLocalRepository
) : IYelpRepository {

    override fun onSearchBusiness(map: Map<String, String>): Single<SearchResponse> {
        return yelpRemoteRepository.onSearchBusiness(map)
    }

    override fun onBusinessDetails(id: String): Single<BusinessDetailResponse> {
        return Single.create {
            Single.merge(
                yelpLocalRepository.onBusinessDetails(id),
                yelpRemoteRepository.onBusinessDetails(id)
            ).subscribe({ source ->
                yelpLocalRepository.saveBusinessDetailResponse(source.id, source)
                it.onSuccess(source)
            }, { error ->
                it.onError(error)
            })
        }
    }
}