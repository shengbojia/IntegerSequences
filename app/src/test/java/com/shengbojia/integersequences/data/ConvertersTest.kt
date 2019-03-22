package com.shengbojia.integersequences.data

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val stringList = listOf<String>("formula 1", "formula 2", "formula 3")

    private val jsonList = "[\"formula 1\",\"formula 2\",\"formula 3\"]"

    @Test
    fun fromListToJson_nonEmptyList_nonEmptyJson() {
        assertEquals(Converters().fromListToJson(stringList), jsonList)
    }

    @Test
    fun fromJsonToList_nonEmptyJson_nonEmptyList() {
        val list = Converters().fromJsonToList(jsonList)

        for (i in 0..2) {
            assertEquals(list[i], stringList[i])
        }
    }

    @Test
    fun fromListToJson_null_emptyJson() {
        assertEquals(Converters().fromListToJson(null), "")
    }

    @Test
    fun fromJsonToList_emptyJson_emptyList() {
        assertEquals(Converters().fromJsonToList(""), emptyList<String>())
    }
}