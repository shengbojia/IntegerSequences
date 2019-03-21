package com.shengbojia.integersequences.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class SequenceSearchResult(
    val data: LiveData<PagedList<Sequence>>,
    val networkErrors: LiveData<String>
)