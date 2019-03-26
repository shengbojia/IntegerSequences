package com.shengbojia.integersequences.repository

enum class ResultStatus {
    RESULT_AND_COUNT_POSITIVE,
    RESULT_AND_COUNT_ZERO,
    RESULT_NULL_AND_COUNT_POSITIVE
}

@Suppress("DataClassPrivateConstructor")
data class ResultState private constructor(
    val status: ResultStatus
) {

    companion object {

        val NORMAL = ResultState(ResultStatus.RESULT_AND_COUNT_POSITIVE)

        val NO_RESULTS = ResultState(ResultStatus.RESULT_AND_COUNT_ZERO)

        val TOO_MANY_RESULTS = ResultState(ResultStatus.RESULT_NULL_AND_COUNT_POSITIVE)
    }
}