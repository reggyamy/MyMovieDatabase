package com.keyta.moviedatabase.data.model

import com.google.gson.annotations.SerializedName

data class VideosResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<VideosItem>
)