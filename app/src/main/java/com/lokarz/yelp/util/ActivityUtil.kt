package com.lokarz.yelp.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat

object ActivityUtil {
    private const val PREV_ACTIVITY = "prev_activity"

    fun popUpScreen(
        context: Context,
        mClazz: Class<*>,
        args: Bundle? = null,
        option: ActivityOptionsCompat? = null,
    ) {
        val intent = Intent(context, mClazz)
        args?.let { intent.putExtras(it) }
        intent.putExtra(PREV_ACTIVITY, context.javaClass.simpleName)

        if (option != null) {
            context.startActivity(intent, option.toBundle())
        } else {
            context.startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(context as AppCompatActivity)
                    .toBundle()
            )
        }
    }

    fun gotoScreen(context: Context, mClazz: Class<*>?, args: Bundle? = null) {
        val intent = Intent(context, mClazz)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        args?.let { intent.putExtras(it) }
        context.startActivity(intent)
    }
}