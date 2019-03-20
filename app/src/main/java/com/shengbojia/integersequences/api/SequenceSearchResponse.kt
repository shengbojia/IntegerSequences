package com.shengbojia.integersequences.api

import com.google.gson.annotations.SerializedName
import com.shengbojia.integersequences.model.Sequence

data class SequenceSearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<Sequence> = emptyList()
)