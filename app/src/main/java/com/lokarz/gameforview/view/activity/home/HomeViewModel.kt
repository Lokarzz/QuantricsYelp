package com.lokarz.gameforview.view.activity.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.gameforview.model.repository.google.GoogleRepository
import com.lokarz.gameforview.model.repository.profile.ProfileRepository
import com.lokarz.gameforview.pojo.google.GoogleAccount
import com.lokarz.gameforview.pojo.profile.ProfileData
import com.lokarz.gameforview.model.util.AdMob
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val googleRepository: GoogleRepository,
    private val adMob: AdMob
) :
    ViewModel() {

    val googleAccount: MutableLiveData<GoogleAccount> = MutableLiveData()
    val backGroundImage: MutableLiveData<String> = MutableLiveData()
    val currentPoints: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    var profileData: ProfileData? = null

    init {
        processGoogleAccountsUi()
        processProfileData()
    }

    fun processProfileData() {
        profileRepository.getProfile().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                profileData = data
                currentPoints.value = "Game Points: ${profileData?.gamingPoints}"
            }
    }

    private fun addRewardPoints() {
        profileData?.gamingPoints = profileData?.gamingPoints?.plus(5)
        currentPoints.value = "Game Points: ${profileData?.gamingPoints}"
        profileRepository.saveProfile(profileData).subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe()
    }


    private fun processGoogleAccountsUi() {
        googleRepository.getData().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                googleAccount.value = data
                val photo = data.photo
                backGroundImage.value =
                    if (!photo.isNullOrEmpty()) photo else "https://img.freepik.com/free-photo/abstract-grunge-decorative-relief-navy-blue-stucco-wall-texture-wide-angle-rough-colored-background_1258-28311.jpg?size=626&ext=jpg"
            }
    }

    fun googleLogout(): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        googleRepository.logout().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { result ->
                mutableLiveData.postValue(result)
            }
        return mutableLiveData
    }

    fun showReward(){
        adMob.showReward().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { addRewardPoints() },
                { err -> error.postValue(err.message) })
    }

}