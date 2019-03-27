package com.shengbojia.integersequences.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

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
}