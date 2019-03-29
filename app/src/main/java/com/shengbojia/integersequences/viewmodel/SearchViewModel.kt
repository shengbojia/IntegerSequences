package com.shengbojia.integersequences.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.shengbojia.integersequences.model.IntSequence
import com.shengbojia.integersequences.model.SequenceSearchResult
import com.shengbojia.integersequences.repository.NetworkState
import com.shengbojia.integersequences.repository.ResultState
import com.shengbojia.integersequences.repository.SequenceRepository

class SearchViewModel(private val repository: SequenceRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    private val resultLiveData: LiveData<SequenceSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }

    val sequences: LiveData<PagedList<IntSequence>> = Transformations.switchMap(resultLiveData) {
        it.data
    }

    val networkState: LiveData<NetworkState> = Transformations.switchMap(resultLiveData) {
        it.networkState
    }

    /*
    val resultState: LiveData<ResultState> = Transformations.switchMap(resultLiveData) {
        it.resultState
    }
    */

    val totalCount: LiveData<Int> = Transformations.switchMap(resultLiveData) {
        it.totalCount
    }

    /**
     * Posts queryLiveData's value, which causes the repository call its search function due to
     * the above Transformations.map.
     */
    fun search(query: String) {
        queryLiveData.value = query

    }

    fun lastQueryValue(): String? = queryLiveData.value
}