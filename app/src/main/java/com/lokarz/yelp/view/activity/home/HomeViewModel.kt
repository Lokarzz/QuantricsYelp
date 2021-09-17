package com.lokarz.yelp.view.activity.home

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.yelp.model.repository.yelp.YelpRepository
import com.lokarz.yelp.util.StringResource
import com.lokarz.yelp.util.location.AppLocation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(
    private val yelpRepository: YelpRepository,
    private val appLocation: AppLocation,
    private val stringResource: StringResource
) :
    ViewModel() {

    val termSearchLiveData: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val locationSearchLiveData: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val myLocationLiveData: MutableLiveData<Location> by lazy {
        MutableLiveData()
    }

    val locationStatusLiveData: MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    val filterLiveData: MutableLiveData<HashMap<String, String>> by lazy {
        MutableLiveData()
    }


    fun searchByTerm(text: String) {
        termSearchLiveData.value = text
    }

    fun searchByLocation(text: String) {
        locationSearchLiveData.value = text
    }

    fun applyFilter(map: HashMap<String, String>?) {
        filterLiveData.value = map
    }

    fun requestLocation() {
        appLocation.requestPermission().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                if (result == true) {
                    getLocation()
                } else {
                    locationStatusLiveData.value = stringResource.locationNotGranted()
                }
            }
    }

    private fun getLocation() {
        locationStatusLiveData.value = stringResource.gettingLocation()
        appLocation.getCurrentLocation().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                myLocationLiveData.value = result
            }, {
                locationStatusLiveData.value = stringResource.locationNotGranted()
            })
    }


}