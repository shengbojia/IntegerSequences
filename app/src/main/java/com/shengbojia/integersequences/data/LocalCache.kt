package com.shengbojia.integersequences.data

import android.util.Log
import androidx.paging.DataSource
import com.shengbojia.integersequences.model.IntSequence
import java.util.concurrent.Executor

/**
 * Layer of abstraction for handling local data source. Ensures methods are triggered on the correct
 * executor.
 */
class LocalCache(
    private val sequenceDao: SequenceDao,
    private val ioExecutor: Executor
) {

    /**
     * Insert into db, on a background thread. After inserting into local db, execute passed function.
     *
     * @param intSequences list of [IntSequence] to insert
     * @param insertFinishedFunc function to call after insert
     */
    fun insert(intSequences: List<IntSequence>, insertFinishedFunc: () -> Unit) {
        ioExecutor.execute {
            Log.d(TAG, "Inserting ${intSequences.size} intSequences in db")
            sequenceDao.insert(intSequences)
            insertFinishedFunc()
        }

    }

    fun search(query: String): DataSource.Factory<Int, IntSequence> {
        Log.d(TAG, "Doing search into Db")
        return sequenceDao.search(query)
    }

    companion object {
        private const val TAG = "CacheLocal"
    }
}