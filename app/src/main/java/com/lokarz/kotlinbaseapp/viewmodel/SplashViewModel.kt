package com.lokarz.kotlinbaseapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.kotlinbaseapp.dagger.module.retrofit.IAppService
import javax.inject.Inject

class SplashViewModel @Inject constructor(var iAppService: IAppService?) : ViewModel() {

    var title : MutableLiveData<String>? = MutableLiveData()
    var srcImg2 : MutableLiveData<String>? = MutableLiveData()



    fun init(){
        srcImg2?.value = "https://lh3.googleusercontent.com/ogw/ADGmqu_xxhhTEpPZqMtokqxs931TV9JG8LAJnFavngPd=s83-c-mo"
        Log.w("asdf", iAppService.toString())
    }

}