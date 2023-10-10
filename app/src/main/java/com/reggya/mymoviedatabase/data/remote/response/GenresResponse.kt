package com.reggya.mymoviedatabase.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GenresResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>
) : Parcelable