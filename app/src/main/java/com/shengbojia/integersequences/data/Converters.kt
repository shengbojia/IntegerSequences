package com.shengbojia.integersequences.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromListToJson(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToList(json: String): List<String> {
        val listType = genericType<List<String>>()
        return Gson().fromJson(json, listType)
    }

    private inline fun <reified T> genericType() = object : TypeToken<T>() {}.type
}