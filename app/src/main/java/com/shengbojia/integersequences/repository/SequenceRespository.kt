package com.shengbojia.integersequences.repository

import android.util.Log
import com.shengbojia.integersequences.api.OeisApi
import com.shengbojia.integersequences.data.LocalCache
import com.shengbojia.integersequences.model.SequenceSearchResult

class SequenceRespository(
    private val api: OeisApi,
    private val cache: LocalCache
) {
    fun search(query: String): SequenceSearchResult {
        Log.d(TAG, "Query: $query")

        val dataSourceFactory = cache.search(query)


    }

    companion object {
        private const val TAG = "RepositorySequence"
    }
}