package com.reggya.mymoviedatabase.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class MovieEntity(

   @ColumnInfo(name = "id")
   @PrimaryKey
   val id: Int?,
   val overview: String?,
   val originalLanguage: String?,
   val originalTitle: String?,
   val video: Boolean?,
   val title: String?,
   val posterPath: String?,
   val backdropPath: String?,
   val releaseDate: String?,
   val popularity: Double?,
   val voteAverage: Double?,
   val adult: Boolean?,
   val voteCount: Int?,
   val genreIds: String?

):Parcelable
