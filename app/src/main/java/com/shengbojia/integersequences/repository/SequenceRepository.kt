package com.shengbojia.integersequences.repository

import android.util.Log
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shengbojia.integersequences.api.OeisApi
import com.shengbojia.integersequences.data.LocalCache
import com.shengbojia.integersequences.model.SequenceSearchResult

class SequenceRepository(
    private val api: OeisApi,
    private val cache: LocalCache
) {

    fun search(query: String): SequenceSearchResult {
        Log.d(TAG, "Query: $query")

        // get data source factory from local cache
        val dataSourceFactory = cache.search(query)

        // every new query starts a new BoundaryCallback whom observes when user reaches the end of
        // the locally stored list of data
        val boundaryCallback = SequenceBoundaryCallback(query, api, cache)
        val networkState = boundaryCallback.networkState
        //val resultState = boundaryCallback.resultState
        val totalCount = boundaryCallback.totalCount

        // get paged list
        val pageListConfig = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE_SIZE)
            .setPrefetchDistance(0)
            .setEnablePlaceholders(true)
            .build()

        val data = LivePagedListBuilder(dataSourceFactory, pageListConfig)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return SequenceSearchResult(data, networkState, /*resultState,*/ totalCount)
    }

    companion object {
        private const val TAG = "RepositorySequence"

        // Might change this, depending on Room db performance and final UI changes
        private const val DATABASE_PAGE_SIZE = 10

        // Singleton instantiation
        @Volatile
        private var INSTANCE: SequenceRepository? = null

        fun getInstance(api: OeisApi, cache: LocalCache): SequenceRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SequenceRepository(api, cache).also { INSTANCE = it }
            }
    }
}