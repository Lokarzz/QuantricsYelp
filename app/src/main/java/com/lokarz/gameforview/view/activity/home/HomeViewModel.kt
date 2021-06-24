package com.lokarz.gameforview.view.activity.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.gameforview.pojo.google.GoogleAccount
import com.lokarz.gameforview.pojo.profile.ProfileData
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.util.RxGoogle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val preferenceUtil: PreferenceUtil,
    private val rxGoogle: RxGoogle
) :
    ViewModel() {

    val googleAccount: MutableLiveData<GoogleAccount> = MutableLiveData()
    val backGroundImage: MutableLiveData<String> = MutableLiveData()
    val currentPoints: MutableLiveData<String> = MutableLiveData()


    init {
        processGoogleAccountsUi()
        processProfileData()
    }

    fun processProfileData() {
        var profileData = GsonUtil.getGson(preferenceUtil, ProfileData::class.java)
        if (profileData == null) {
            profileData = ProfileData()
        }
        currentPoints.value = "Game Points: ${profileData.gamingPoints}"
    }

    fun addRewardPoints() {
        var profileData = GsonUtil.getGson(preferenceUtil, ProfileData::class.java)
        if (profileData == null) {
            profileData = ProfileData()
        }
        profileData.gamingPoints += 5
        currentPoints.value = "Game Points: ${profileData.gamingPoints}"
        preferenceUtil.saveData(profileData::class.simpleName, GsonUtil.getGsonString(profileData))
    }


    private fun processGoogleAccountsUi() {
        val googleAccountData = GsonUtil.getGson(preferenceUtil, GoogleAccount::class.java)
        googleAccount.value = googleAccountData
        val photo = googleAccountData?.photo
        backGroundImage.value =
            if (!photo.isNullOrEmpty()) photo else "https://img.freepik.com/free-photo/abstract-grunge-decorative-relief-navy-blue-stucco-wall-texture-wide-angle-rough-colored-background_1258-28311.jpg?size=626&ext=jpg"
    }

    fun googleLogout(): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        rxGoogle.logout().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread()).subscribe { result ->
                mutableLiveData.postValue(result)
            }
        return mutableLiveData
    }

}