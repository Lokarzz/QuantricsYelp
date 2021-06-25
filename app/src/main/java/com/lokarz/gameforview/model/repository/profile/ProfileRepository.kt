package com.lokarz.gameforview.model.repository.profile

import com.lokarz.gameforview.pojo.profile.ProfileData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val localProfileRepository: LocalProfileRepository) {


    fun saveProfile(profileData: ProfileData?): Single<Boolean> {
        return localProfileRepository.saveProfile(profileData)
    }

    fun getProfile(): Single<ProfileData> {
        return localProfileRepository.getProfile()
    }
}