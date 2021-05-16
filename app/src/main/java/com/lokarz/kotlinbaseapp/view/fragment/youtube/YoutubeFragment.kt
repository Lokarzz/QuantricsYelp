package com.lokarz.kotlinbaseapp.view.fragment.youtube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.databinding.DataBindingUtil
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.databinding.FragmentYoutubeBinding
import com.lokarz.kotlinbaseapp.view.base.BaseFragment
import com.lokarz.kotlinbaseapp.viewmodel.YoutubeViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import javax.inject.Inject

class YoutubeFragment() : BaseFragment() {


    @Inject
    lateinit var youtubeViewModel: YoutubeViewModel;

    var ytp1: YouTubePlayer? = null
    var ytp2: YouTubePlayer? = null

    companion object {
        fun newInstance(): YoutubeFragment {
            return YoutubeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val fragmentYoutubeBinding = DataBindingUtil.inflate<FragmentYoutubeBinding>(
            inflater,
            R.layout.fragment_youtube,
            container,
            false
        )
        fragmentYoutubeBinding.youtubeViewModel = youtubeViewModel
        fragmentYoutubeBinding.lifecycleOwner = this

        mView = fragmentYoutubeBinding.root

        initMotionLayout()
        initYtpv()

        return mView
    }

    private fun initMotionLayout() {
        val motionLayout: MotionLayout = mView.findViewById(R.id.motionLayout)
        motionLayout.setTransitionListener(object : TransitionAdapter() {

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenUnlike, R.id.offScreenLike -> {
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.like)
                        onSwipe()
                    }
                }
            }
        })
    }

    private fun checkOnReady() {
        if (ytp1 != null && ytp2 != null) {
            updateData()
        }
//        Log.w("YoutubeFragment", "Observable")
//        Observable.interval(500, TimeUnit.MILLISECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
////            .repeatUntil((BooleanSupplier { ytp1 != null && ytp2 != null }))
//            .skipUntil( (ObservableSource<Any> { ytp1!= null && ytp2 != null}))
//            .subscribe {
//                updateData()
//                Log.w("YoutubeFragment", "initOnReady")
//            }
    }

    private fun onSwipe() {
        updateData()
    }

    private fun updateData() {
        Log.w("YoutubeFragment", "updateData")
        ytp1?.loadVideo("8hGGVW_mGEI", 0f)
        ytp2?.cueVideo("cOrYjwAJnrk", 0f)
    }

    private fun initYtpv() {
        val ytpv1: YouTubePlayerView = mView.findViewById(R.id.ytpv1);
        lifecycle.addObserver(ytpv1)
        ytpv1.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                ytp1 = youTubePlayer
                checkOnReady()
            }
        })

        val ytpv2: YouTubePlayerView = mView.findViewById(R.id.ytpv2);
        lifecycle.addObserver(ytpv2)
        ytpv2.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                ytp2 = youTubePlayer
                checkOnReady()

            }
        })
    }

}