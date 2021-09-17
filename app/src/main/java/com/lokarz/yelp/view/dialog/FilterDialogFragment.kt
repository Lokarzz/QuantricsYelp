package com.lokarz.yelp.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lokarz.yelp.R
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.Constant
import com.lokarz.yelp.util.ViewUtil

class FilterDialogFragment : BottomSheetDialogFragment() {

    var onApplyFilterListener: AppListener.OnApplyFilterListener? = null
    private var spnSort: AppCompatSpinner? = null
    var defaultData: HashMap<String, String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFilterView()

    }

    private fun initFilterView() {
        initSort()
        initClickListener()
    }

    private fun initClickListener() {
        view?.let {
            ViewUtil.setClickListener(it, getOnClickListener(), R.id.btn_apply_filter)
        }
    }

    private fun getOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            when (it.id) {
                R.id.btn_apply_filter -> {
                    onApplyFilter()
                }
            }
        }
    }

    private fun onApplyFilter() {
        val defaultData = defaultData ?: HashMap()
        defaultData[Constant.Yelp.SORT_BY] = getSortValue()

        onApplyFilterListener?.onApplyFilter(defaultData)

        dismiss()
    }

    private fun getSortValue(): String {
        val sortSelected = spnSort?.selectedItem.toString()
        var ret = sortSelected
        when (sortSelected) {
            getString(R.string.best_match) -> {
                ret = Constant.Yelp.BEST_MATCH
            }
            getString(R.string.review_count) -> {
                ret = Constant.Yelp.REVIEW_COUNT
            }
            getString(R.string.distance) -> {
                ret = Constant.Yelp.DISTANCE
            }
            getString(R.string.rating) -> {
                ret = Constant.Yelp.RATING
            }
        }
        return ret
    }


    private fun initSort() {
        spnSort = view?.findViewById(R.id.spn_sort)
        val sortVal = defaultData?.get(Constant.Yelp.SORT_BY) ?: ""
        spnSort?.setSelection(getSortPos(sortVal))

    }


    private fun getSortPos(sortValue: String): Int {
        var ret: Int
        val array = listOf(
            Constant.Yelp.BEST_MATCH,
            Constant.Yelp.RATING,
            Constant.Yelp.REVIEW_COUNT,
            Constant.Yelp.DISTANCE
        )
        ret = array.indexOf(sortValue)
        if (ret < 0) {
            ret = 0
        }
        return ret
    }


}