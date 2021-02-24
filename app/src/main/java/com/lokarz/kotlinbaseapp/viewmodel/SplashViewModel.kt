package com.lokarz.kotlinbaseapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {

    var title : MutableLiveData<String>? = MutableLiveData()
    var srcImg2 : MutableLiveData<String>? = MutableLiveData()


    fun init(){
        srcImg2?.value = "https://lh3.googleusercontent.com/ogw/ADGmqu_xxhhTEpPZqMtokqxs931TV9JG8LAJnFavngPd=s83-c-mo"
    }
}