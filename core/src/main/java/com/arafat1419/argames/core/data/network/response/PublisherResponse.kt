package com.arafat1419.argames.core.data.network.response

import com.google.gson.annotations.SerializedName

data class PublisherResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,
)