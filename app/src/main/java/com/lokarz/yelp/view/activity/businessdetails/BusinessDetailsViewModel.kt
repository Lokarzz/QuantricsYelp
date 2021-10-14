package com.lokarz.yelp.view.activity.businessdetails

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.yelp.model.repository.YelpRepository
import com.lokarz.yelp.model.repository.poko.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.helper.resource.ResourceHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BusinessDetailsViewModel(
    private val yelpRepository: YelpRepository,
    private val stringResource: ResourceHelper
) :
    ViewModel() {

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val businessDetailsLiveData: MutableLiveData<BusinessDetailResponse> by lazy {
        MutableLiveData()
    }
    val displayAddressLiveData: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val snippetLiveData: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val showHoursLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
    val showPhotosLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
    val showCategoriesLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
    val imageBannerLiveData: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val nameLiveData: MutableLiveData<String> by lazy {
        MutableLiveData()
    }


    fun getBusinessDetails(id: String) {
        yelpRepository.onBusinessDetails(id)
            .doOnSubscribe { compositeDisposable.add(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                businessDetailsLiveData.value = response
                initDisplayAddress()
                initSnippet()
                initPhotos()
                initCategories()
                hoursOfOperation()
            }
    }

    fun setBanner(image: String) {
        imageBannerLiveData.value = image
    }

    fun setName(name: String) {
        nameLiveData.value = name
    }


    private fun initSnippet() {
        val data = businessDetailsLiveData.value
        val claimText = stringResource.getClaimText(data?.isClaimed ?: false)
        val stringArray = ArrayList<String>()
        stringArray.add(claimText)
        data?.price?.let {
            stringArray.add(it)
        }
        snippetLiveData.value = TextUtils.join(" - ", stringArray)
    }

    private fun initPhotos() {
        val photos = businessDetailsLiveData.value?.photos
        showPhotosLiveData.value = photos?.isNotEmpty() == true
    }

    private fun initCategories() {
        val categories = businessDetailsLiveData.value?.categories
        showCategoriesLiveData.value = categories?.isNotEmpty() == true
    }

    private fun initDisplayAddress() {
        val displayAddressArray = businessDetailsLiveData.value?.location?.displayAddress

        displayAddressArray ?: return
        val displayAddress = TextUtils.join(" ", displayAddressArray)
        displayAddressLiveData.value = displayAddress
    }

    private fun hoursOfOperation() {
        val openHours = businessDetailsLiveData.value?.hours?.get(0)?.open
        showHoursLiveData.value = openHours != null
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}