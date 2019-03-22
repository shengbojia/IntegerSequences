package com.shengbojia.integersequences.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shengbojia.integersequences.model.IntSequence

/**
 * Local database that holds the list of sequences.
 */
@Database(
    entities = [IntSequence::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SequenceDatabase : RoomDatabase() {

    abstract fun sequenceDao(): SequenceDao

    companion object {

        @Volatile
        private var INSTANCE: SequenceDatabase? = null

        fun getInstance(context: Context): SequenceDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                SequenceDatabase::class.java, "Github.db")
                .build()
    }
}