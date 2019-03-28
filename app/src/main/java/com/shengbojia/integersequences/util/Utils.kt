package com.shengbojia.integersequences.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout

/**
 * Static, general-use util methods.
 */
object Utils {

    /**
     * Hides the keyboard in current activity.
     */
    fun hideSoftKeyboard(activity: Activity?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

    fun changeFrameLayoutGravity(view: View, gravityType: Int) {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = gravityType
        view.layoutParams = params
    }
}