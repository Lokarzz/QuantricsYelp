package com.lokarz.gameforview.model.repository.profile

import com.lokarz.gameforview.pojo.profile.ProfileData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileLocalRepository: ProfileLocalRepository) {


    fun saveProfile(profileData: ProfileData?): Single<Boolean> {
        return profileLocalRepository.saveProfile(profileData)
    }

    fun getProfile(): Single<ProfileData> {
        return profileLocalRepository.getProfile()
    }

    fun addPoints(points: Int = 1): Single<Boolean> {
        return Single.create {
            getProfile().subscribe { profileData ->
                profileData.gamingPoints = profileData.gamingPoints?.plus(points)
                saveProfile(profileData).subscribe { result ->
                    it.onSuccess(result)
                }
            }
        }
    }

    fun decreasePoints(points: Int = 1): Single<Boolean> {
        return Single.create {
            getProfile().subscribe { profileData ->
                profileData.gamingPoints = profileData.gamingPoints?.minus(points)
                saveProfile(profileData).subscribe { result ->
                    it.onSuccess(result)
                }
            }
        }
    }
}