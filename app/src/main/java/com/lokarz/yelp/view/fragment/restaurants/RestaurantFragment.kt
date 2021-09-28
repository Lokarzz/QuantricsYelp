package com.lokarz.yelp.view.fragment.restaurants

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lokarz.yelp.R
import com.lokarz.yelp.databinding.FragmentRestaurantBinding
import com.lokarz.yelp.pojo.yelp.search.Businesses
import com.lokarz.yelp.util.ActivityUtil
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.Constant
import com.lokarz.yelp.view.activity.businessdetails.BusinessDetailsActivity
import com.lokarz.yelp.view.activity.home.HomeViewModel
import com.lokarz.yelp.view.adapter.recylerview.RestaurantAdapter
import com.lokarz.yelp.view.base.BaseFragment
import javax.inject.Inject

class RestaurantFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: RestaurantViewModel

    lateinit var binding: FragmentRestaurantBinding

    private val arrayList = ArrayList<Businesses>()
    private var restaurantAdapter: RestaurantAdapter? = null
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_restaurant,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRestaurantView()
    }

    private fun initRestaurantView() {
        initRecyclerView()
        initDefaultSearch()
        observeSearchResponse()
        observeTermLiveData()
        observeLocationLiveData()
        observeMyLocationLiveData()
        observeFilterLiveData()
        observeOnBottomReach()
    }


    private fun initDefaultSearch() {
        viewModel.searchBusiness()
    }

    private fun observeFilterLiveData() {
        homeViewModel.filterLiveData.observe(viewLifecycleOwner) {
            restaurantAdapter?.clearData()
            viewModel.applyAdditionalFilter(it)
        }
    }

    private fun observeMyLocationLiveData() {
        homeViewModel.myLocationLiveData.observe(viewLifecycleOwner) {
            restaurantAdapter?.clearData()
            viewModel.searchByLocation(currentLocation = it)
        }
    }

    private fun observeLocationLiveData() {
        homeViewModel.locationSearchLiveData.observe(viewLifecycleOwner) {
            restaurantAdapter?.clearData()
            viewModel.searchByLocation(location = it)
        }
    }

    private fun observeTermLiveData() {
        homeViewModel.termSearchLiveData.observe(viewLifecycleOwner) {
            restaurantAdapter?.clearData()
            viewModel.searchByTerm(it)
        }
    }

    private fun observeSearchResponse() {
        viewModel.businesses.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()) {
                restaurantAdapter?.onNewData(it)
            }
        }
    }


    private fun observeOnBottomReach() {
        viewModel.bottomReachLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                restaurantAdapter?.onBottomReachedListener = onBottomReach()
            }
        }
    }


    private fun onBottomReach(): AppListener.OnBottomReachedListener {
        return object : AppListener.OnBottomReachedListener {
            override fun onBottomReached() {
                viewModel.businessLoadMore()
            }

        }
    }

    private fun initRecyclerView() {
        binding.rvRestaurant.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        restaurantAdapter = RestaurantAdapter(this, arrayList)
        restaurantAdapter?.onItemClickListener = getOnItemClickListener()
        binding.rvRestaurant.adapter = restaurantAdapter

    }

    private fun getOnItemClickListener(): AppListener.OnItemClickListener<Businesses> {
        return object : AppListener.OnItemClickListener<Businesses> {
            override fun onItemClick(item: Businesses) {
                toDetailsScreen(item)
            }
        }
    }

    private fun toDetailsScreen(businesses: Businesses) {
        val args = Bundle()
        args.putString(Constant.App.BUSINESS_ID, businesses.id)
        activity?.let {
            ActivityUtil.popUpScreen(it, BusinessDetailsActivity::class.java, args)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                updateLayoutManager(
                    4,
                    binding.rvRestaurant.layoutManager as StaggeredGridLayoutManager
                )

            }
            Configuration.ORIENTATION_PORTRAIT -> {
                updateLayoutManager(
                    2,
                    binding.rvRestaurant.layoutManager as StaggeredGridLayoutManager
                )

            }
        }
    }

    private fun updateLayoutManager(
        spanCount: Int,
        staggeredGridLayoutManager: StaggeredGridLayoutManager
    ) {
        staggeredGridLayoutManager.spanCount = spanCount
    }

}