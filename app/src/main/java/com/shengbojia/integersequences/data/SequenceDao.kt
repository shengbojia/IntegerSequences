package com.shengbojia.integersequences.data

import androidx.paging.DataSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shengbojia.integersequences.model.IntSequence

/**
 * Data Access Object for accessing [IntSequence] table.
 */
interface SequenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(intSequences: List<IntSequence>)

    /**
     * Similar to API search, seeks [IntSequence]s that contain the query as a sub-sequence.
     *
     * @param query string of numbers separated by commas
     */
    @Query("SELECT * FROM sequences_table WHERE (sequenceSnippet LIKE '%,' || :query || ',%') " +
            "OR (sequenceSnippet LIKE '&,' || :query) " +
            "OR (sequenceSnippet LIKE :query || ',%')" +
            "ORDER BY numberId DESC")
    fun search(query: String): DataSource.Factory<Int, IntSequence>
}