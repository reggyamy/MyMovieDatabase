package com.reggya.mymoviedatabase.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
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
): Parcelable
