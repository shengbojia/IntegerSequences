package com.shengbojia.integersequences.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.shengbojia.integersequences.api.OeisApi
import com.shengbojia.integersequences.api.searchAndHandleResponse
import com.shengbojia.integersequences.data.LocalCache
import com.shengbojia.integersequences.model.IntSequence

/**
 * Observes when the user reaches the end of locally cached data and then requests new data from the network.
 */
class SequenceBoundaryCallback(
    private val query: String,
    private val api: OeisApi,
    private val cache: LocalCache
) : PagedList.BoundaryCallback<IntSequence>() {

    // Oeis returns 10 items per call, starts at 0
    private var lastRequestedItem = 0

    // mutable version to post values in
    private val _networkState = MutableLiveData<NetworkState>()

    // immutable version exposed for use elsewhere in package
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _resultState = MutableLiveData<ResultState>()

    val resultState: LiveData<ResultState>
        get() = _resultState

    // prevent triggering multiple requests before result returns
    private var isRequestInProgress = false

    /**
     * When the database is empty, queries backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d(TAG, "onZero")
        requestAndSaveData(query, true)
    }

    /**
     * When the end item of the list in the db is loaded, queries for more items from backend.
     */
    override fun onItemAtEndLoaded(itemAtEnd: IntSequence) {
        Log.d(TAG, "onItemAtEndLoaded ${resultState.value} $lastRequestedItem")
        if (lastRequestedItem > resultState.value!!.totalCount) {
            return
        }
        requestAndSaveData(query, false)
    }

    /*
    private fun initialRequest(query: String) {
        if (isRequestInProgress) return

        _networkState.value = NetworkState.LOADING
        isRequestInProgress = true
        searchAndHandleResponse(api, query, lastRequestedItem, { sequences, count ->
            if (sequences.isEmpty() && count > 0) {

                _resultState.value = ResultState.TOO_MANY_RESULTS

            } else if (count == 0) {

                _resultState.value = ResultState.NO_RESULTS

            } else {

                _resultState.value = ResultState.NORMAL
                cache.insert(sequences) {
                    totalCount = count
                    lastRequestedItem += 10
                }
            }
            isRequestInProgress = false
            _networkState.value = NetworkState.LOADED
        }, {errorMsg ->
            _networkState.value = NetworkState.error(errorMsg)
            isRequestInProgress = false
        })

    }
    */

    private fun determineResultState(sequences: List<IntSequence>, count: Int) {
        if (sequences.isEmpty() && count > 0) {

            _resultState.value = ResultState.tooManyResults(count)

        } else if (count == 0) {

            _resultState.value = ResultState.NO_RESULTS

        } else {

            _resultState.value = ResultState.normal(count)
        }

    }

    private fun requestAndSaveData(query: String, initialRequest: Boolean) {
        if (isRequestInProgress) return

        _networkState.postValue(NetworkState.LOADING)
        isRequestInProgress = true
        searchAndHandleResponse(api, query, lastRequestedItem, { sequences, count ->

            if (initialRequest) {
                determineResultState(sequences, count)
            }

            cache.insert(sequences) {
                // update status
                lastRequestedItem += 10
                _networkState.postValue(NetworkState.LOADED)
                isRequestInProgress = false
            }
        }, { errorMsg ->
            // Safe as LiveData calls this on main thread
            _networkState.postValue(NetworkState.error(errorMsg))
            isRequestInProgress = false
        })
    }

    private fun handleTooManyResults() {

    }

    companion object {
        private const val TAG = "CallbackBoundary"
    }
}