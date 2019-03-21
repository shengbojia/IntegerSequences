package com.shengbojia.integersequences.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "sequences_table",
    indices = [Index("numberID")]
)
data class IntSequence(
    @PrimaryKey
    @SerializedName("number")
    val numberId: Int,
    @SerializedName("data")
    val sequenceSnippet: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("formula")
    val formulas: List<String>
)