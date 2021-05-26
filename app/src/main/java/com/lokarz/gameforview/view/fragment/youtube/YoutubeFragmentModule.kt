package com.lokarz.gameforview.view.fragment.youtube

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.view.base.BaseFragmentModule
import com.lokarz.gameforview.viewmodel.YoutubeViewModel
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