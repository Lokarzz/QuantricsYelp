package com.lokarz.kotlinbaseapp.view.fragment.youtube

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.kotlinbaseapp.dagger.factory.ViewModelProviderFactory
import com.lokarz.kotlinbaseapp.view.base.BaseFragmentModule
import com.lokarz.kotlinbaseapp.viewmodel.YoutubeViewModel
import dagger.Module
import dagger.Provides


@Module
class YoutubeFragmentModule : BaseFragmentModule<YoutubeFragment>() {


    @Provides
    fun provideFactory(): ViewModelProvider.Factory {
        val viewModel = YoutubeViewModel()
        return ViewModelProviderFactory(viewModel);
    }

    @Provides
    fun provideViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelFactory: ViewModelProvider.Factory
    ): YoutubeViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelFactory
        ).get(YoutubeViewModel::class.java)
    }


//    @Provides
//    fun provideAdMobViewModel(
//        viewModelStoreOwner: ViewModelStoreOwner,
//        @Nullable context: Context
//    ): AdMobViewModel {
//        val adMobViewModel = ViewModelProvider(viewModelStoreOwner).get(AdMobViewModel::class.java)
//        MobileAds.initialize(context) {}
//        adMobViewModel.initReward(context)
//        return adMobViewModel
//    }


}