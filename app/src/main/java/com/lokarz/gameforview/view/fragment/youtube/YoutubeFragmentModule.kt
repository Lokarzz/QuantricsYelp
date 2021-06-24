package com.lokarz.gameforview.view.fragment.youtube

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.MainApplication
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.view.base.BaseFragmentModule
import dagger.Module
import dagger.Provides


@Module
class YoutubeFragmentModule : BaseFragmentModule<YoutubeFragment>() {


    @Provides
    fun providePreferenceUtil(mainApplication: MainApplication): PreferenceUtil {
        return PreferenceUtil(mainApplication)
    }

    @Provides
    fun provideFactory(firebaseFirestore: FirebaseFirestore, preferenceUtil: PreferenceUtil): ViewModelProvider.Factory {
        val viewModel = YoutubeViewModel(firebaseFirestore, preferenceUtil)
        return ViewModelProviderFactory(viewModel)
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