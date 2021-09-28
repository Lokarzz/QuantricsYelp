package com.lokarz.yelp.view.fragment.restaurants

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.yelp.model.repository.yelp.YelpRepository
import com.lokarz.yelp.pojo.yelp.search.Businesses
import com.lokarz.yelp.util.Constant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RestaurantViewModel(
    private val yelpRepository: YelpRepository
) :
    ViewModel() {

    val businesses: MutableLiveData<List<Businesses>> by lazy {
        MutableLiveData()
    }
    val bottomReachLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
    val shimmerLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
    val showNoResult: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }


    private val searchParams = HashMap<String, String>()

    init {
        searchParams[Constant.Yelp.LIMIT] = Constant.Yelp.SEARCH_LIMIT.toString()
    }

    fun searchByTerm(term: String) {
        searchParams[Constant.Yelp.TERM] = term
        searchBusiness()
    }

    fun applyAdditionalFilter(map: HashMap<String, String>) {
        searchParams.putAll(map)
        searchBusiness()
    }

    fun searchByLocation(location: String? = null, currentLocation: Location? = null) {
        clearLocationParams()

        location?.let {
            searchParams[Constant.Yelp.LOCATION] = it
        }
        currentLocation?.let {
            searchParams[Constant.Yelp.LATITUDE] = currentLocation.latitude.toString()
            searchParams[Constant.Yelp.LONGITUDE] = currentLocation.longitude.toString()
        }
        searchBusiness()
    }

    private fun clearLocationParams() {
        searchParams.remove(Constant.Yelp.LOCATION)
        searchParams.remove(Constant.Yelp.LATITUDE)
        searchParams.remove(Constant.Yelp.LONGITUDE)
    }

    fun searchBusiness() {
        initDefaultParams()
        shimmerLiveData.value = true
        yelpRepository.searchBusiness(searchParams).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                shimmerLiveData.value = false
                businesses.value = response.businesses
                showNoResult.value = response.businesses.isNullOrEmpty()
                bottomReachLiveData.value = response.businesses.size >= Constant.Yelp.SEARCH_LIMIT
            }
    }

    private fun initDefaultParams() {
        val lat = searchParams[Constant.Yelp.LATITUDE]
        val lng = searchParams[Constant.Yelp.LONGITUDE]
        val location = searchParams[Constant.Yelp.LOCATION]
        if (lat.isNullOrEmpty() && lng.isNullOrEmpty() && location.isNullOrEmpty()) {
            searchParams[Constant.Yelp.LOCATION] = Constant.Yelp.DEFAULT_LOCATION
        }
        searchParams[Constant.Yelp.OFFSET] = "0"
    }

    fun businessLoadMore() {
        val offset = searchParams[Constant.Yelp.OFFSET]?.toInt() ?: 0
        searchParams[Constant.Yelp.OFFSET] = (offset + Constant.Yelp.SEARCH_LIMIT).toString()
        yelpRepository.searchBusiness(searchParams).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                businesses.postValue(response.businesses)
                bottomReachLiveData.value = response.businesses.size >= Constant.Yelp.SEARCH_LIMIT
            }

    }

}