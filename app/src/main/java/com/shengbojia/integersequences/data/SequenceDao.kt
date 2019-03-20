package com.shengbojia.integersequences.data

import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shengbojia.integersequences.model.Sequence

/**
 * Data Access Object for accessing [Sequence] table.
 */
interface SequenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sequences: List<Sequence>)

    /**
     * Similar to API search, seeks [Sequence]s that contain the query as a sub-sequence.
     *
     * @param query string of numbers separated by commas
     */
    @Query("SELECT * FROM sequences_table WHERE (sequenceSnippet LIKE '%,' || :query || ',%') " +
            "OR (sequenceSnippet LIKE '&,' || :query) " +
            "OR (sequenceSnippet LIKE :query || ',%')" +
            "ORDER BY numberId DESC")
    fun search(query: String): DataSource.Factory<Int, Sequence>
}