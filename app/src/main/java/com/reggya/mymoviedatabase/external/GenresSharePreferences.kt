package com.reggya.mymoviedatabase.external

import android.content.Context
import com.reggya.mymoviedatabase.data.remote.response.GenresItem

class GenresSharePreferences(context: Context) {

    private val preferences = context.getSharedPreferences("GENRES_KEY", Context.MODE_PRIVATE)

    companion object{
        const val GENRE = "GENRES"
    }

    fun setGenres(genres: List<GenresItem>?) {
        val editor = preferences.edit()
        val json = JSONUtils.toJson(genres)
        editor.putString(GENRE, json)
        editor.apply()
    }

    fun getGenres(): List<GenresItem?>? {
        val data = preferences.getString(GENRE, null)
        return JSONUtils.fromJsonToList<GenresItem>(data)
    }

}