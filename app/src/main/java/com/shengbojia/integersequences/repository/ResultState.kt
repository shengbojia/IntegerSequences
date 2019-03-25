package com.shengbojia.integersequences.repository

enum class ResultStatus {
    NORMAL,
    NO_RESULTS,
    TOO_MANY_RESULTS
}

@Suppress("DataClassPrivateConstructor")
data class ResultState private constructor(
    val status: ResultStatus,
    val totalCount: Int
) {

    companion object {

        val NO_RESULTS = ResultState(ResultStatus.NO_RESULTS, 0)

        fun normal(totalCount: Int) = ResultState(ResultStatus.NORMAL, totalCount)

        fun tooManyResults(totalCount: Int) = ResultState(ResultStatus.TOO_MANY_RESULTS, totalCount)
    }
}