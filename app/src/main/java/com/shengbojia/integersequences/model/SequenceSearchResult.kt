package com.shengbojia.integersequences.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class SequenceSearchResult(
    val data: LiveData<PagedList<IntSequence>>,
    val networkErrors: LiveData<String>
)