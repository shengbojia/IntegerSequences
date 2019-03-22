package com.shengbojia.integersequences.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.shengbojia.integersequences.data.Converters

@Entity(
    tableName = "sequences_table",
    indices = [Index("numberId")]
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
    val formulas: List<String>?
)