package com.lokarz.gameforview.view.fragment.youtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.databinding.DataBindingUtil
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.FragmentYoutubeBinding
import com.lokarz.gameforview.util.AppListener
import com.lokarz.gameforview.view.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class YoutubeFragment(val listener: Listener) : BaseFragment() {


    @Inject
    lateinit var youtubeViewModel: YoutubeViewModel;

    var ytp1: YouTubePlayer? = null
    var ytp2: YouTubePlayer? = null
    var ytpv1: YouTubePlayerView? = null;

    interface Listener {
        fun onRefreshPoints()
    }

    companion object {
        fun newInstance(listener: Listener): YoutubeFragment {
            return YoutubeFragment(listener)
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
        onPointsAdded()

        return mView
    }

    private fun onPointsAdded() {
       youtubeViewModel.isPointsAdded.observe(viewLifecycleOwner, {
           if (it == true){
               listener.onRefreshPoints()
           }
       })
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
            loadYoutubeCard()
            addListener()
        }
    }

    private fun addListener() {
        ytp1 ?: return
        ytp1?.addListener(object : AppListener.AppYoutube() {
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                youtubeViewModel.processPoints(second)
            }
        })
    }

    private fun onSwipe() {
        youtubeViewModel.updateYoutubeCard()
    }

    private fun loadYoutubeCard() {
        youtubeViewModel.youtubeCard.observe(this, {
            ytpv1?.visibility = View.INVISIBLE
            ytp1?.loadVideo(it.firstCard?.videoId!!, 0f)
            Observable.just(1).delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    ytpv1?.visibility = View.VISIBLE
                    ytp2?.cueVideo(it.secondCard?.videoId!!, 0f)
                }

        })
    }

    private fun initYtpv() {
        ytpv1 = mView.findViewById(R.id.ytpv1)
        lifecycle.addObserver(ytpv1!!)
        ytpv1?.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
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