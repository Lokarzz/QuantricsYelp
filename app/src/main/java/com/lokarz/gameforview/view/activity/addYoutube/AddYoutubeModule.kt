package com.lokarz.gameforview.view.activity.addYoutube

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.model.api.retrofit.youtube.IYoutubeService
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class AddYoutubeModule : BaseActivityModule<AddYoutubeActivity>() {

    @Provides
    fun providePreferenceUtil(context: Context): PreferenceUtil {
        return PreferenceUtil(context)
    }

    @Provides
    fun provideFactory(
        firebaseFirestore: FirebaseFirestore,
        preferenceUtil: PreferenceUtil,
        iYoutubeService: IYoutubeService
    ): ViewModelProvider.Factory {
        val addYoutubeViewModel = AddYoutubeViewModel(firebaseFirestore, preferenceUtil, iYoutubeService)
        return ViewModelProviderFactory(addYoutubeViewModel)
    }

    @Provides
    fun provideAddYoutubeViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelProvider: ViewModelProvider.Factory
    ): AddYoutubeViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelProvider
        ).get(AddYoutubeViewModel::class.java)
    }


}