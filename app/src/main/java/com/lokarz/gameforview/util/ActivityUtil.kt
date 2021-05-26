package com.lokarz.gameforview.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ActivityUtil {
    companion object {
        const val PREV_ACTIVITY = "prev_activity"
        const val REQ_CODE = 99

        fun popUpScreen(context: Context, mClazz: Class<*>?, args: Bundle?) {
            popUpScreen(context,
                mClazz,
                args,
                REQ_CODE)
        }

        fun popUpScreen(context: Context, mClazz: Class<*>?, requestCode: Int) {
            popUpScreen(context, mClazz, null, requestCode)
        }

        fun popUpScreen(context: Context, mClazz: Class<*>?, args: Bundle?, requestCode: Int) {
            val intent = Intent(context, mClazz)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            if (args != null) {
                intent.putExtras(args)
            }
            intent.putExtra(PREV_ACTIVITY, context.javaClass.simpleName)
            try {
                (context as AppCompatActivity).startActivityForResult(intent, requestCode)
            } catch (e: Exception) {
                // do nothing
            }
        }


        fun popUpScreen(context: Context, mClazz: Class<*>?) {
            popUpScreen(context, mClazz, null)
        }

        fun popUpScreen(view: View, mClazz: Class<*>?) {
            popUpScreen(view.context, mClazz, null)
        }

        fun goToScreen(context: Context, intent: Intent) {
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            context.startActivity(intent)
        }

        fun goToScreen(context: Context, mClazz: Class<*>?, args: Bundle?) {
            val intent = Intent(context, mClazz)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra(PREV_ACTIVITY, context.javaClass.simpleName)
            if (args != null) {
                intent.putExtras(args)
            }
            context.startActivity(intent)
        }

        fun goToScreen(context: Context, mClazz: Class<*>?) {
            goToScreen(context, mClazz, null)
        }

    }
}