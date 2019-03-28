package com.shengbojia.integersequences.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shengbojia.integersequences.data.SequenceDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ClearCacheWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override val coroutineContext: CoroutineDispatcher
        get() = Dispatchers.IO

    override suspend fun doWork(): Result = withContext(coroutineContext) {
        try {
            val database = SequenceDatabase.getInstance(applicationContext)
            database.sequenceDao().deleteAll()

            Result.success()
        } catch (ex: Exception) {
            Log.e("Worker", ex.message)
            Result.failure()
        }
    }


}