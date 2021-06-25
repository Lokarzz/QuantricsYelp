package com.lokarz.gameforview.view.activity.addYoutube

import android.content.ClipboardManager
import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivityAddYoutubeBinding
import com.lokarz.gameforview.pojo.youtube.YoutubeData
import com.lokarz.gameforview.util.ActivityUtil
import com.lokarz.gameforview.util.Constant
import com.lokarz.gameforview.view.activity.home.HomeActivity
import com.lokarz.gameforview.view.base.BaseActivity
import javax.inject.Inject

class AddYoutubeActivity : BaseActivity() {

    @Inject
    lateinit var addYoutubeViewModel: AddYoutubeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = DataBindingUtil.setContentView<ActivityAddYoutubeBinding>(
            this,
            R.layout.activity_add_youtube
        )
        activityBinding.addYoutubeViewModel = addYoutubeViewModel
        activityBinding.lifecycleOwner = this
        initToolBar()

        initClickListener(R.id.btn_add, R.id.btn_paste)


    }

    private fun initClickListener(vararg ids: Int) {
        for (id in ids) {
            findViewById<View>(id).setOnClickListener(getOnClickListener())
        }
    }

    private fun getOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            when (it.id) {
                R.id.btn_add -> {
                    addYoutubeId()
                }
                R.id.btn_paste -> {
                    pasteToField()
                }
            }
        }
    }

    private fun pasteToField() {
        val clipboard = (getSystemService(Context.CLIPBOARD_SERVICE)) as? ClipboardManager
        val textToPaste = clipboard?.primaryClip?.getItemAt(0)?.text
        val etVideoIdLink: AppCompatEditText = findViewById(R.id.et_video_id_link)
        etVideoIdLink.setText(textToPaste)
    }

    private fun initToolBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            ActivityUtil.gotoScreen(this, HomeActivity::class.java)
        }

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)

    }


    private fun addYoutubeId() {
        val etVideoIdLink: AppCompatEditText = findViewById(R.id.et_video_id_link)
        val videoIdLink = etVideoIdLink.text.toString()
        val youtubeData = YoutubeData(addYoutubeViewModel.getVideoId(videoIdLink))
        addYoutubeViewModel.addYoutubeVideoId(youtubeData).observe(this) {
            when (it) {
                Constant.Success.VALID_ID -> {
                    showToast("Youtube Video Successfully Added")
                }
                Constant.Error.INVALID_ID -> {
                    showToast("Invalid Youtube Video")

                }
                Constant.Error.SOMETHING_WENT_WRONG -> {
                    showToast("Something went wrong")
                }
                Constant.Error.ID_ALREADY_ADDED -> {
                    showToast("Youtube Video is already added")
                }
            }
        }
    }


}