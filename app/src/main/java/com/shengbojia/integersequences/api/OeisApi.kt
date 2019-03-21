package com.shengbojia.integersequences.api

import android.util.Log
import com.shengbojia.integersequences.model.Sequence
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "ApiOeis"

fun searchAndHandleResponse(
    api: OeisApi,
    query: String,
    startAt: Int,
    onSuccessFunc: (sequences: List<Sequence>, count: Int) -> Unit,
    onErrorFunc: (errorMsg: String) -> Unit
) {
    Log.d(TAG, "Query: $query, $startAt")
    api.search(
        query = query,
        startAt = startAt
        ).enqueue(
        object : Callback<SequenceSearchResponse> {

            override fun onFailure(call: Call<SequenceSearchResponse>, t: Throwable) {
                Log.d(TAG, "Failed request")
                onErrorFunc(t.message ?: "Unknown error Code 1")
            }

            override fun onResponse(
                call: Call<SequenceSearchResponse>,
                response: Response<SequenceSearchResponse>
            ) {
                Log.d(TAG, "Got some sort of response, $response")
                if (response.isSuccessful) {
                    // Look at README.md for more info on how this database returns results
                    val sequences: List<Sequence> = response.body()?.results ?: emptyList()
                    val totalCount = response.body()?.count ?: 0
                    onSuccessFunc(sequences, totalCount)
                } else {
                    onErrorFunc(response.errorBody()?.string() ?: "Unknown error Code 2")
                }
            }
        }
    )
}
/**
 * Api communication setup via Retrofit.
 */
interface OeisApi {
    @GET("/search")
    fun search(
        @Query("q") query: String,
        @Query("fmt") format: String = "json",
        @Query("start") startAt: Int = 0
    ): Call<SequenceSearchResponse>

    companion object {
        private const val BASE_URL = "https://oeis.org/"

        fun create(): OeisApi {
            val logger =  HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d(TAG, it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OeisApi::class.java)
        }
    }
}