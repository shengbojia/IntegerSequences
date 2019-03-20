package com.shengbojia.integersequences.data

import android.util.Log
import androidx.paging.DataSource
import com.shengbojia.integersequences.model.Sequence
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
     * @param sequences list of [Sequence] to insert
     * @param insertFinishedFunc function to call after insert
     */
    fun insert(sequences: List<Sequence>, insertFinishedFunc: () -> Unit) {
        ioExecutor.execute {
            Log.d(TAG, "Inserting ${sequences.size} sequences in db")
            sequenceDao.insert(sequences)
            insertFinishedFunc()
        }
    }

    fun search(query: String): DataSource.Factory<Int, Sequence> {
        Log.d(TAG, "Doing search into Db")
        return sequenceDao.search(query)
    }

    companion object {
        private const val TAG = "CacheLocal"
    }
}