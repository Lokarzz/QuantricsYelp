package com.lokarz.gameforview.view.fragment.youtube

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.MainApplication
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.model.repository.firebase.FirebaseRemoteRepository
import com.lokarz.gameforview.model.repository.firebase.FirebaseRepository
import com.lokarz.gameforview.model.repository.profile.ProfileLocalRepository
import com.lokarz.gameforview.model.repository.profile.ProfileRepository
import com.lokarz.gameforview.util.Preference
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.view.base.BaseFragmentModule
import dagger.Module
import dagger.Provides


@Module
class YoutubeFragmentModule : BaseFragmentModule<YoutubeFragment>() {


    @Provides
    fun provideFirebaseRemoteRepository(firebaseFirestore: FirebaseFirestore): FirebaseRemoteRepository {
        return FirebaseRemoteRepository(firebaseFirestore)
    }

    @Provides
    fun provideFirebaseRepository(firebaseRemoteRepository: FirebaseRemoteRepository): FirebaseRepository {
        return FirebaseRepository(firebaseRemoteRepository)
    }


    @Provides
    fun providedLocalProfileRepository(preference: Preference): ProfileLocalRepository {
        return ProfileLocalRepository(preference)
    }

    @Provides
    fun provideProfileRepository(profileLocalRepository: ProfileLocalRepository): ProfileRepository {
        return ProfileRepository(profileLocalRepository)
    }

    @Provides
    fun provideFactory(profileRepository: ProfileRepository,
                      firebaseRepository: FirebaseRepository
    ): ViewModelProvider.Factory {
        val viewModel = YoutubeViewModel(profileRepository, firebaseRepository)
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