package com.lokarz.yelp.view.activity.businessdetails

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.yelp.model.repository.yelp.YelpRepository
import com.lokarz.yelp.pojo.yelp.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.util.StringResource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BusinessDetailsViewModel(
    private val yelpRepository: YelpRepository,
    private val stringResource: StringResource
) :
    ViewModel() {

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

    fun getBusinessDetails(id: String) {
        yelpRepository.getBusinessDetails(id).subscribeOn(Schedulers.io())
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

}