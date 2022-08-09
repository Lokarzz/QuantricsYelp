package com.lokarz.yelp.view.activity.businessdetails

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager
import com.lokarz.yelp.databinding.ActivityBusinessDetailsBinding
import com.lokarz.yelp.model.repository.poko.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.model.repository.poko.businessdetails.Hours
import com.lokarz.yelp.util.Constant
import com.lokarz.yelp.view.adapter.recylerview.BusinessCategoriesAdapter
import com.lokarz.yelp.view.adapter.recylerview.BusinessHoursAdapter
import com.lokarz.yelp.view.adapter.recylerview.BusinessPhotoAdapter
import com.lokarz.yelp.view.base.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BusinessDetailsActivity : BaseActivity() {


    @Inject
    lateinit var viewModel: BusinessDetailsViewModel

    private lateinit var binding: ActivityBusinessDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBusinessDetailsBinding.inflate(layoutInflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)

        initTransition()
        initBusinessDetailsView()
    }

    private fun initTransition() {
        postponeEnterTransition()
        Single.just(1)
            .delay(50, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ ->
                startPostponedEnterTransition()
            }
    }


    private fun initBusinessDetailsView() {
        initSharedElements()
        observeBusinessData()
        getBusinessData()
        initNavigation()
    }

    private fun initSharedElements() {
        initBannerElement()
        initNameElement()
    }

    private fun initNameElement() {
        val name = intent.getStringExtra(Constant.App.NAME)
        name?.let {
            ViewCompat.setTransitionName(binding.tvName, it)
            viewModel.setName(it)
        }
    }

    private fun initBannerElement() {
        val imageUrl = intent.getStringExtra(Constant.App.IMAGE_URL)
        imageUrl?.let {
            ViewCompat.setTransitionName(binding.ivBanner, it)
            viewModel.setBanner(it)
        }
    }

    private fun getBusinessData() {
        val businessId = intent.getStringExtra(Constant.App.BUSINESS_ID)
        businessId ?: return
        viewModel.getBusinessDetails(businessId)
    }

    private fun observeBusinessData() {
        viewModel.businessDetailsLiveData.observe(this) {
            initPhotos(it)
            initCategories(it)
            initHoursOfOperation(it.hours)
        }
    }

    private fun initHoursOfOperation(hours: List<Hours>?) {
        val open = hours?.get(0)?.open
        open ?: return

        binding.rvHoursOfOperation.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvHoursOfOperation.adapter = BusinessHoursAdapter(open)
    }

    private fun initPhotos(data: BusinessDetailResponse) {
        val photos = data.photos
        if (photos.isEmpty()) {
            return
        }
        binding.rvBusinessPhotos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBusinessPhotos.adapter = BusinessPhotoAdapter(this, photos)
    }

    private fun initCategories(data: BusinessDetailResponse) {
        val categories = data.categories ?: ArrayList()
        if (categories.isEmpty()) {
            return
        }
        binding.rvBusinessCategories.layoutManager = FlexboxLayoutManager(this)
        binding.rvBusinessCategories.adapter = BusinessCategoriesAdapter(categories)
    }


    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finishAfterTransition()
        }

    }


}