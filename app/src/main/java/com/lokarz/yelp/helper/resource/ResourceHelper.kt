package com.lokarz.yelp.helper.resource

import android.content.Context
import com.lokarz.yelp.R

class ResourceHelper(private var context: Context) {


    fun getClaimText(claimed: Boolean): String {
        var claimText = context.getString(R.string.claimed)
        if (!claimed) {
            claimText = context.getString(R.string.not_claimed)
        }
        return claimText
    }

    fun gettingLocation(): String {
        return context.getString(R.string.getting_location)
    }

    fun locationNotGranted(): String {
        return context.getString(R.string.location_not_granted)
    }
}