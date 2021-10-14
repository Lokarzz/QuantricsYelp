package com.lokarz.yelp.view.activity.home

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.yelp.helper.location.LocationHelper
import com.lokarz.yelp.helper.resource.ResourceHelper
import com.lokarz.yelp.model.repository.YelpRepository
import com.lokarz.yelp.util.AppEnum
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class HomeViewModel(
    private val yelpRepository: YelpRepository,
    private val locationHelper: LocationHelper,
    private val stringResource: ResourceHelper
) :
    ViewModel() {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

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

    val filterVisibility: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val visibilityRelay: PublishSubject<Boolean> by lazy {
        PublishSubject.create()
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

    fun onScrollChange(dy: Int) {
        val state = if (dy >= 0) AppEnum.ScrollState.UP else AppEnum.ScrollState.DOWN
        filterVisibility.value = AppEnum.ScrollState.UP == state
    }

    fun requestLocation() {
        locationHelper.requestPermission().subscribeOn(Schedulers.io())
            .doOnSubscribe { compositeDisposable.add(it) }
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
        locationHelper.getCurrentLocation().subscribeOn(Schedulers.io())
            .doOnSubscribe { compositeDisposable.add(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                myLocationLiveData.value = result
            }, {
                locationStatusLiveData.value = stringResource.locationNotGranted()
            })
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}