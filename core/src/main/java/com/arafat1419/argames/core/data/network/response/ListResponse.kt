package com.arafat1419.argames.core.data.network.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
	@field:SerializedName("count")
	val count: String? = null,

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: String? = null,

	@field:SerializedName("results")
	val results: List<T>? = null,
)