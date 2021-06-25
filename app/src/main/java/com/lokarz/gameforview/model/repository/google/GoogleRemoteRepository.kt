package com.lokarz.gameforview.model.repository.google

import com.lokarz.gameforview.pojo.google.GoogleAccount
import com.lokarz.gameforview.util.RxGoogle
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GoogleRemoteRepository @Inject constructor(
    private val rxGoogle: RxGoogle,
) {

    fun login(): Single<Boolean> {
        return rxGoogle.login()
    }

    fun getData(): Single<GoogleAccount> {
        return rxGoogle.getData()
    }
    fun logout(): Single<Boolean> {
        return rxGoogle.logout()
    }

}