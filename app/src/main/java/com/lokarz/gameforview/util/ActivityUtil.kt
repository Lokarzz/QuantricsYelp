package com.lokarz.gameforview.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

class ActivityUtil {
    companion object {
        private const val PREV_ACTIVITY = "prev_activity"

        fun popUpScreen(
            context: Context,
            mClazz: Class<*>,
            args: Bundle? = null,
            activityResultLauncher: ActivityResultLauncher<Intent>? = null
        ) {
            val intent = Intent(context, mClazz)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            args?.let { intent.putExtras(it) }
            intent.putExtra(PREV_ACTIVITY, context.javaClass.simpleName)

            if (activityResultLauncher != null) {
                activityResultLauncher.launch(intent)
            } else {
                context.startActivity(intent)
            }

        }

        fun gotoScreen(context: Context, mClazz: Class<*>?, args: Bundle? = null) {
            val intent = Intent(context, mClazz)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            //        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(PREV_ACTIVITY, context.javaClass.simpleName)
            if (args != null) {
                intent.putExtras(args)
            }
            context.startActivity(intent)
        }

    }
}