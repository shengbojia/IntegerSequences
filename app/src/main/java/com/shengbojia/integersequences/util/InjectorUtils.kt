package com.shengbojia.integersequences.util

import android.content.Context
import com.shengbojia.integersequences.api.OeisApi
import com.shengbojia.integersequences.data.LocalCache
import com.shengbojia.integersequences.data.SequenceDatabase
import com.shengbojia.integersequences.repository.SequenceRepository
import com.shengbojia.integersequences.viewmodel.SearchViewModelFactory
import java.util.concurrent.Executors

/**
 * Static methods to handle object creation.
 */
object InjectorUtils {
    // TODO: Look into Dagger 2 dependency injections

    /**
     * Creates an instance of [LocalCache] based on the database DAO.
     */
    private fun getLocalCache(context: Context): LocalCache {
        val db = SequenceDatabase.getInstance(context)
        return LocalCache(db.sequenceDao(), Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [SequenceRepository] based on the [OeisApi] and a [LocalCache].
     */
    private fun getSequenceRepository(context: Context) =
        SequenceRepository.getInstance(OeisApi.create(), getLocalCache(context))


    /**
     * Provides an instance of [SearchViewModelFactory] based on a [SequenceRepository].
     */
    fun provideSearchViewModelFactory(context: Context): SearchViewModelFactory {
        val repository = getSequenceRepository(context)
        return SearchViewModelFactory(repository)
    }
}