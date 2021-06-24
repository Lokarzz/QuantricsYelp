package com.lokarz.gameforview.view.activity.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.util.RxGoogle
import com.lokarz.gameforview.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class LoginActivityModule : BaseActivityModule<LoginActivity>() {

    @Provides
    fun providePreferenceUtil(context: Context): PreferenceUtil {
        return PreferenceUtil(context)
    }

    @Provides
    fun provide(context: Context) : RxGoogle {
        return RxGoogle(context as AppCompatActivity)
    }

//    @Provides
//    fun provideFactory(iAppService: IAppService) : ViewModelProvider.Factory{
//        val splashViewModel = SplashViewModel(iAppService)
//        return ViewModelProviderFactory(splashViewModel);
//    }
//
//    @Provides
//    fun provideSplashViewModel(viewModelStoreOwner: ViewModelStoreOwner, viewModelProvider:ViewModelProvider.Factory): SplashViewModel {
//        return ViewModelProvider(viewModelStoreOwner, viewModelProvider).get(SplashViewModel::class.java)
//    }


}