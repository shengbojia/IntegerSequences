package com.shengbojia.integersequences.repository

import android.util.Log
import androidx.paging.PagedList
import com.shengbojia.integersequences.api.OeisApi
import com.shengbojia.integersequences.data.LocalCache
import com.shengbojia.integersequences.model.Sequence

class SequenceBoundaryCallback(
    private val query: String,
    private val api: OeisApi,
    private val cache: LocalCache
) : PagedList.BoundaryCallback<Sequence>() {

    // prevent triggering multiple requests before result returns
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        Log.d(TAG, "onZero")

    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

    }



    companion object {
        private const val TAG = "CallbackBoundary"
    }
}