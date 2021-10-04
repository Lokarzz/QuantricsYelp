package com.lokarz.yelp.view.activity.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.lokarz.yelp.R
import com.lokarz.yelp.databinding.ActivityHomeBinding
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.ViewUtil
import com.lokarz.yelp.view.base.BaseActivity
import com.lokarz.yelp.view.dialog.FilterDialogFragment
import javax.inject.Inject

class HomeActivity : BaseActivity() {


    @Inject
    lateinit var homeViewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding

    private var filterMap: HashMap<String, String>? = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        initHomeView()
    }

    private fun initHomeView() {
        initClickListener()
        initSearch()
        initAddressSearch()
        observerMyLocationStatus()
    }

    private fun observerMyLocationStatus() {
        homeViewModel.myLocationLiveData.observe(this) {
            onCurrentLocation()
        }

        homeViewModel.locationStatusLiveData.observe(this) {
            showToast(it)
        }

    }

    private fun onCurrentLocation() {
        showToast(getString(R.string.location_updated))
        binding.etLocation.setText(getString(R.string.current_location))
    }

    private fun initAddressSearch() {
        binding.etLocation.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_DONE -> {
                    searchByLocation()
                }
            }
            true
        }
    }

    private fun initSearch() {
        binding.etLocation.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_DONE -> {
                    searchByTerm()
                }
            }
            true
        }
    }

    private fun searchByTerm() {
        val searchText = binding.etTerm.text.toString()
        homeViewModel.searchByTerm(searchText)
        ViewUtil.hideKeyboard(this)
    }

    private fun searchByLocation() {
        val searchText = binding.etLocation.text.toString()
        homeViewModel.searchByLocation(searchText)
        ViewUtil.hideKeyboard(this)
    }

    private fun initClickListener() {
        ViewUtil.setClickListener(
            getOnClickListener(), binding.ivMyLocation,
            binding.ivFilter,
        )
    }


    private fun getOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            when (it.id) {
                R.id.iv_my_location -> {
                    homeViewModel.requestLocation()
                }
                R.id.iv_filter -> {
                    showFilter()
                }

            }
        }
    }

    private fun showFilter() {
        val filterDialogFragment = FilterDialogFragment()
        filterDialogFragment.defaultData = filterMap
        filterDialogFragment.onApplyFilterListener = onApplyFilterListener()
        filterDialogFragment.show(supportFragmentManager, "")
    }

    private fun onApplyFilterListener(): AppListener.OnApplyFilterListener {
        return object : AppListener.OnApplyFilterListener {
            override fun onApplyFilter(map: HashMap<String, String>?) {
                homeViewModel.applyFilter(map)
            }
        }
    }


}