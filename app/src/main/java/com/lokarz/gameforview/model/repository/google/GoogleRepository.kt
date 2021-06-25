package com.lokarz.gameforview.model.repository.google

import com.lokarz.gameforview.pojo.google.GoogleAccount
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GoogleRepository @Inject constructor(
    private val googleLocalRepository: GoogleLocalRepository,
    private val remoteRepository: GoogleRemoteRepository
) {


    fun login(): Single<Boolean> {
        return remoteRepository.login()
    }

    fun getData(): Single<GoogleAccount> {
        return Single.create {
            val local = googleLocalRepository.getData().subscribe { data ->
                it.onSuccess(data)
            }
            remoteRepository.getData().subscribe { data ->
                local.dispose()
                googleLocalRepository.saveData(data).subscribe()
                it.onSuccess(data)
            }
        }
    }

    fun logout(): Single<Boolean> {
        return Single.create {
            remoteRepository.logout().subscribe { _ ->
                googleLocalRepository.clearData()
                it.onSuccess(true)
            }
        }
    }
}