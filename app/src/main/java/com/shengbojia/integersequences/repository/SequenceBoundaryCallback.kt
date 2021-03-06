package com.shengbojia.integersequences.repository

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
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

    /*
    private val _resultState = MutableLiveData<ResultState>()

    val resultState: LiveData<ResultState>
        get() = _resultState

    */

    private val _totalCount = MutableLiveData<Int>()

    val totalCount: LiveData<Int>
        get() = _totalCount

    // prevent triggering multiple requests before result returns
    private var isRequestInProgress = false

    /**
     * When the database is empty, queries backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        Log.d(TAG, "onZero")
        requestAndSaveData(query) { sequences, count ->
            // in bg thread now
            _totalCount.postValue(count)
            requestSuccess(sequences)
        }
        Log.d(TAG, "onZero total count: ${totalCount.value}")
    }

    /**
     * When the end item of the list in the db is loaded, queries for more items from backend.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: IntSequence) {
        Log.d(TAG, "onItemAtEndLoaded ${totalCount.value} $lastRequestedItem")
        if (lastRequestedItem > totalCount.value ?: 0) {
            return
        }

        requestAndSaveData(query) { sequences, _ ->
            requestSuccess(sequences)
        }
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

    /*
    /**
     * Only called on the initial call to the network. Determines the type of result returned by the network
     * from the query, and sets the total count of matching results.
     */
    @WorkerThread
    private fun determineResultState(sequences: List<IntSequence>, count: Int) {
        if (sequences.isEmpty() && count > 0) {

            _resultState.postValue(ResultState.TOO_MANY_RESULTS)

        } else if (count == 0) {

            _resultState.postValue(ResultState.NO_RESULTS)

        } else {

            _resultState.postValue(ResultState.NORMAL)
        }

        _totalCount.postValue(count)

    }
    */

    /**
     * On the event of a successful network request, update the index of last requested item, network state to loaded,
     * and whether request is in progress.
     *
     * @param sequences list of [IntSequence] to insert into db
     * @param count number of matching results as reported by network
     */
    @WorkerThread
    private fun requestSuccess(sequences: List<IntSequence>) {
        // TODO: Could do something with count, like check if data has changed
        cache.insert(sequences) {
            // update status
            lastRequestedItem += 10
            _networkState.postValue(NetworkState.LOADED)
            isRequestInProgress = false
        }
    }

    /**
     * Requests data from the Api and inserts the data into the db.
     *
     * @param query to be searched
     */
    @MainThread
    private fun requestAndSaveData(query: String,
                                   onSuccess: (List<IntSequence>, Int) -> Unit) {
        if (isRequestInProgress) return

        _networkState.value = NetworkState.LOADING
        isRequestInProgress = true
        searchAndHandleResponse(api, query, lastRequestedItem, { sequences, count ->

            onSuccess(sequences, count)

        }, { errorMsg ->
            // Safe as LiveData calls this on main thread
            _networkState.postValue(NetworkState.error(errorMsg))
            isRequestInProgress = false
        })
    }


    companion object {
        private const val TAG = "CallbackBoundary"
    }
}