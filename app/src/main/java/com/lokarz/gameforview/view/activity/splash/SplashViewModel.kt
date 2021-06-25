package com.lokarz.gameforview.view.activity.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.gameforview.model.repository.google.GoogleRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var googleRepository: GoogleRepository) :
    ViewModel() {

    var title: MutableLiveData<String>? = MutableLiveData()
    var srcImg2: MutableLiveData<String>? = MutableLiveData()

    fun googleLogin(): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        googleRepository.login().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                mutableLiveData.postValue(result)
            }
        return mutableLiveData
    }

}