package com.lokarz.yelp.view.fragment.restaurants

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.lokarz.yelp.databinding.FragmentRestaurantBinding
import com.lokarz.yelp.databinding.ItemRestaurantBinding
import com.lokarz.yelp.model.repository.poko.search.Businesses
import com.lokarz.yelp.util.ActivityUtil
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.Constant
import com.lokarz.yelp.view.activity.businessdetails.BusinessDetailsActivity
import com.lokarz.yelp.view.activity.home.HomeViewModel
import com.lokarz.yelp.view.adapter.recylerview.RestaurantAdapter
import com.lokarz.yelp.view.base.BaseFragment
import javax.inject.Inject
import androidx.core.util.Pair as UtilPair
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
        binding = FragmentRestaurantBinding.inflate(inflater, container, false)

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
        binding.rvRestaurant.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                println("onScroll $dy")
                homeViewModel.onScrollChange(dy = dy)
            }
        })
        restaurantAdapter = RestaurantAdapter(this, arrayList)
        restaurantAdapter?.onItemClickListener = getOnItemClickListener()
        binding.rvRestaurant.adapter = restaurantAdapter

    }

    private fun getOnItemClickListener(): AppListener.OnItemViewClickListener<Businesses, ItemRestaurantBinding> {
        return object : AppListener.OnItemViewClickListener<Businesses, ItemRestaurantBinding> {
            override fun onItemViewClick(item: Businesses, binding: ItemRestaurantBinding) {
                toDetailsScreen(item, binding)
            }

        }
    }

    private fun toDetailsScreen(businesses: Businesses, binding: ItemRestaurantBinding) {
        val args = Bundle()
        args.putString(Constant.App.BUSINESS_ID, businesses.id)
        args.putString(Constant.App.IMAGE_URL, businesses.imageUrl)
        args.putString(Constant.App.NAME, businesses.name)
        activity?.let {
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(it,
                UtilPair.create(binding.ivImage, businesses.imageUrl),
                UtilPair.create(binding.tvName, businesses.name))
            ActivityUtil.popUpScreen(it, BusinessDetailsActivity::class.java, args, option = option)
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