package com.lokarz.gameforview.model.repository.profile

import com.lokarz.gameforview.pojo.profile.ProfileData
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.util.Preference
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileLocalRepository @Inject constructor(private val preference: Preference) {

    fun saveProfile(profileData: ProfileData?): Single<Boolean> {
        return Single.create {
            preference.saveData(
                ProfileData::class.simpleName,
                GsonUtil.getGsonString(profileData)
            )
            it.onSuccess(true)
        }

    }


    fun getProfile(): Single<ProfileData> {
        return Single.create {
            val profile = GsonUtil.getGson(preference, ProfileData::class.java)
            if (profile != null) {
                it.onSuccess(profile)
            } else {
                it.onSuccess(ProfileData())
            }
        }
    }

}