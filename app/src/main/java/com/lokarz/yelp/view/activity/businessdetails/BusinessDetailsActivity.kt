package com.lokarz.yelp.view.activity.businessdetails

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.lokarz.yelp.R
import com.lokarz.yelp.databinding.ActivityBusinessDetailsBinding
import com.lokarz.yelp.pojo.yelp.businessdetails.BusinessDetailResponse
import com.lokarz.yelp.pojo.yelp.businessdetails.Hours
import com.lokarz.yelp.util.Constant
import com.lokarz.yelp.view.adapter.recylerview.BusinessCategoriesAdapter
import com.lokarz.yelp.view.adapter.recylerview.BusinessHoursAdapter
import com.lokarz.yelp.view.adapter.recylerview.BusinessPhotoAdapter
import com.lokarz.yelp.view.base.BaseActivity
import javax.inject.Inject

class BusinessDetailsActivity : BaseActivity() {


    @Inject
    lateinit var viewModel: BusinessDetailsViewModel

    private lateinit var binding: ActivityBusinessDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_business_details
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initBusinessDetailsView()


    }

    private fun initBusinessDetailsView() {
        observeBusinessData()
        getBusinessData()
        initNavigation()
    }

    private fun getBusinessData() {
        val businessId = intent.getStringExtra(Constant.App.BUSINESS_ID)
        businessId ?: return
        viewModel.getBusinessDetails(businessId)
    }

    private fun observeBusinessData() {
        viewModel.businessDetailsLiveData.observe(this) {
            initBusinessData(it)
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

    private fun initBusinessData(data: BusinessDetailResponse) {
        loadBannerImage(data.imageUrl)
    }

    private fun initPhotos(data: BusinessDetailResponse) {
        val photos = data.photos
        if (photos.isEmpty()) {
            return
        }
        binding.rvBusinessPhotos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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

    private fun loadBannerImage(bannerUrl: String) {
        var image = bannerUrl
        if (image.isEmpty()) {
            image = Constant.Image.ITEM_DEFAULT_IMAGE
        }
        Glide.with(this).load(image).into(binding.ivBanner)
    }


    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }


}