package com.shengbojia.integersequences.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.shengbojia.integersequences.api.OeisApi
import com.shengbojia.integersequences.api.searchAndHandleResponse
import com.shengbojia.integersequences.data.LocalCache
import com.shengbojia.integersequences.model.Sequence

/**
 * Observes when the user reaches the end of locally cached data and then requests new data from the network.
 */
class SequenceBoundaryCallback(
    private val query: String,
    private val api: OeisApi,
    private val cache: LocalCache
) : PagedList.BoundaryCallback<Sequence>() {

    // Oeis returns 10 items per call, starts at 0
    private var lastRequestedItem = 0

    // total amount of matching sequences
    private var totalCount = 0

    // mutable version to post values in
    private val _networkErrors = MutableLiveData<String>()

    // immutable version exposed for use elsewhere in package
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // prevent triggering multiple requests before result returns
    private var isRequestInProgress = false

    /**
     * When the database is empty, queries backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d(TAG, "onZero")
        requestAndSaveData(query)
    }

    /**
     * When the end item of the list in the db is loaded, queries for more items from backend.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Sequence) {
        Log.d(TAG, "onItemAtEndLoaded")
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchAndHandleResponse(api, query, lastRequestedItem, { sequences, count ->
            cache.insert(sequences) {
                // update status
                totalCount = count
                lastRequestedItem += 10
                isRequestInProgress = false
            }
        }, { errorMsg ->
            // Safe as LiveData calls this on main thread
            _networkErrors.postValue(errorMsg)
            isRequestInProgress = false
        })
    }

    companion object {
        private const val TAG = "CallbackBoundary"
    }
}