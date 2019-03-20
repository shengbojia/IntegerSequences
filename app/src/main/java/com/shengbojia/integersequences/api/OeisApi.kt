package com.shengbojia.integersequences.api

import android.util.Log
import com.shengbojia.integersequences.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "ApiOeis"

interface OeisApi {
    @GET("/search")
    fun search(
        @Query("q") query: String,
        @Query("fmt") format: String = "json",
        @Query("start") startAt: Int = 0
    ): Call<SequenceSearchResponse>

    companion object {

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