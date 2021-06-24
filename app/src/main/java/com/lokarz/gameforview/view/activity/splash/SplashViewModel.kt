package com.lokarz.gameforview.view.activity.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.gameforview.api.retrofit.google.IGoogleService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor(var iGoogleService: IGoogleService?) : ViewModel() {

    var title: MutableLiveData<String>? = MutableLiveData()
    var srcImg2: MutableLiveData<String>? = MutableLiveData()

}