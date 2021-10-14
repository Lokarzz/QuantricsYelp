package com.lokarz.yelp.model.repository

import com.lokarz.yelp.helper.sharedPreference.SharedPreferenceHelper
import com.lokarz.yelp.model.repository.poko.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.model.repository.poko.search.SearchResponse
import com.lokarz.yelp.util.GsonUtil
import io.reactivex.rxjava3.core.Single

class YelpLocalRepository(private val sharedPreferenceHelper: SharedPreferenceHelper) :
    IYelpRepository {

    fun saveBusinessDetailResponse(key: String, businessDetailResponse: BusinessDetailResponse) {
        sharedPreferenceHelper.putString(
            key,
            GsonUtil.toString(businessDetailResponse)
        )
    }

    override fun onSearchBusiness(map: Map<String, String>): Single<SearchResponse> {
        return Single.create {}
    }

    override fun onBusinessDetails(id: String): Single<BusinessDetailResponse> {
        return Single.create {
            val businessDetailResponse = GsonUtil.getGson(
                sharedPreferenceHelper.getString(id),
                BusinessDetailResponse::class.java
            )
            businessDetailResponse?.let { source ->
                it.onSuccess(source)
            }
        }

    }
}