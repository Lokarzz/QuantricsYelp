package com.lokarz.kotlinbaseapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.databinding.FragmentSplashBinding
import com.lokarz.kotlinbaseapp.view.base.BaseFragment
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashFragment : BaseFragment() {


    @Inject
    lateinit var splashViewModel: SplashViewModel;


    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentSplashBinding = DataBindingUtil.inflate<FragmentSplashBinding>(
            inflater,
            R.layout.fragment_splash,
            container,
            false
        )
        fragmentSplashBinding.splashViewModel = splashViewModel
        fragmentSplashBinding.lifecycleOwner = this

        splashViewModel.init()
        return fragmentSplashBinding.root
    }


}