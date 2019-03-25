package com.shengbojia.integersequences.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.shengbojia.integersequences.repository.NetworkState
import com.shengbojia.integersequences.repository.ResultState


data class SequenceSearchResult(
    val data: LiveData<PagedList<IntSequence>>,
    val networkState: LiveData<NetworkState>,
    val resultState: LiveData<ResultState>
)