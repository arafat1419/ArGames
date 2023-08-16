package com.arafat1419.argames.core.data.network.response

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("background_image")
    val backgroundImage: String? = null,

    @field:SerializedName("released")
    val released: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("playtime")
    val playtime: String? = null,

    @field:SerializedName("website")
    val website: String? = null,

    @field:SerializedName("short_screenshots")
    val shortScreenshots: List<ScreenshotResponse>? = null,

    @field:SerializedName("publishers")
    val publishers: List<PublisherResponse>? = null,
)

