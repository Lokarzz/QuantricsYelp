package com.lokarz.gameforview.util

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.lokarz.gameforview.view.base.BaseFragment

class FragmentUtil {
    companion object {
        fun goToFragment(fm: FragmentManager, fragment: BaseFragment, @IdRes container: Int) {
            try {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val ft = fm.beginTransaction()
                ft.replace(container, fragment)
                ft.commit()
            } catch (e: Exception) {
                Log.e("replaceFragment", e.toString())
            }
        }

        fun popFragment(fm: FragmentManager, fragment: BaseFragment, destination: Int) {
            try {
                val ft = fm.beginTransaction()
                ft.replace(destination, fragment)
                ft.addToBackStack(fragment.javaClass.simpleName)
                ft.commit()
            }catch (e: Exception) {
                Log.e("addFragment", e.toString())
            }

        }
    }
}