package com.shengbojia.integersequences.repository

import android.util.Log
import androidx.paging.LivePagedListBuilder
import com.shengbojia.integersequences.api.OeisApi
import com.shengbojia.integersequences.data.LocalCache
import com.shengbojia.integersequences.model.SequenceSearchResult

class SequenceRespository(
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
        val networkErrors = boundaryCallback.networkErrors

        // get paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return SequenceSearchResult(data, networkErrors)
    }

    companion object {
        private const val TAG = "RepositorySequence"

        // Might change this, depending on Room db performance and final UI changes
        private const val DATABASE_PAGE_SIZE = 15

        // Singleton instantiation
        @Volatile
        private var INSTANCE: SequenceRespository? = null

        fun getInstance(api: OeisApi, cache: LocalCache): SequenceRespository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SequenceRespository(api, cache).also { INSTANCE = it }
            }
    }
}