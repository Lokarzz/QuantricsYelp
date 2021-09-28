package com.lokarz.yelp.view.activity.businessdetails

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val rvHours = findViewById<RecyclerView>(R.id.rv_hours_of_operation)
        rvHours?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvHours?.adapter = BusinessHoursAdapter(open)
    }

    private fun initBusinessData(data: BusinessDetailResponse) {
        loadBannerImage(data.imageUrl)
    }

    private fun initPhotos(data: BusinessDetailResponse) {
        val photos = data.photos
        if (photos.isEmpty()) {
            return
        }
        val rvPhotos = findViewById<RecyclerView>(R.id.rv_business_photos)
        rvPhotos?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPhotos?.adapter = BusinessPhotoAdapter(this, photos)
    }

    private fun initCategories(data: BusinessDetailResponse) {
        val categories = data.categories ?: ArrayList()
        if (categories.isEmpty()) {
            return
        }
        val rvPhotos = findViewById<RecyclerView>(R.id.rv_business_categories)
        rvPhotos?.layoutManager = FlexboxLayoutManager(this)
        rvPhotos?.adapter = BusinessCategoriesAdapter(categories)
    }

    private fun loadBannerImage(bannerUrl: String) {
        var image = bannerUrl
        if (image.isEmpty()) {
            image = Constant.Image.ITEM_DEFAULT_IMAGE
        }
        Glide.with(this).load(image).into(findViewById(R.id.iv_banner))
    }

    private fun initClickListener() {
        setNavigationOnClickListener(

        )
    }

    private fun setNavigationOnClickListener(vararg ids: Int) {
        for (id in ids) {
//            findViewById<View>(id).setOnClickListener(getOnClickListener())
        }
    }

    private fun getOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
//            when (it.id) {
//                R.id.vg_add_youtube_video -> {
//                    val map = HashMap<String, String>()
//                    map[Constant.Yelp.TERM] = "starbucks"
//                    map[Constant.Yelp.LATITUDE] = "10.2571968"
//                    map[Constant.Yelp.LONGITUDE] = "123.8322375"
//                    homeViewModel.searchBusiness(map)
//                }
//                R.id.vg_logout -> {
//
//                }
//            }
//            drawerLayout?.closeDrawer(GravityCompat.START)
        }
    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }


}