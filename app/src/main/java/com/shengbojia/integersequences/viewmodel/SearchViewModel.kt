package com.shengbojia.integersequences.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.shengbojia.integersequences.model.Sequence
import com.shengbojia.integersequences.model.SequenceSearchResult
import com.shengbojia.integersequences.repository.SequenceRepository

class SearchViewModel(private val repository: SequenceRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()

    private val resultLiveData: LiveData<SequenceSearchResult> = Transformations.map(queryLiveData) {
        repository.search(it)
    }

    val sequences: LiveData<PagedList<Sequence>> = Transformations.switchMap(resultLiveData) {
        it.data
    }
    val networkErrors: LiveData<String> = Transformations.switchMap(resultLiveData) {
        it.networkErrors
    }

    /**
     * Posts queryLiveData's value, which causes the repository call its search function due to
     * the above Transformations.map.
     */
    fun search(query: String) {
        queryLiveData.value = query
    }



}