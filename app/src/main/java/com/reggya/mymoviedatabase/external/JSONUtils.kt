package com.reggya.mymoviedatabase.external

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JSONUtils {

    fun <T> toJson(data: T): String {
        return Gson().toJson(data)
    }

    inline fun <reified T> fromJsonToList(jsonString: String?): List<T?>? {
        val listType = object : TypeToken<List<T>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}