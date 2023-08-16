package com.arafat1419.argames.core.domain.model

data class GameDomain(
    val id: Int? = null,
    val name: String? = null,
    val backgroundImage: String? = null,
    val released: String? = null,
    val description: String? = null,
    val rating: String? = null,
    val playtime: String? = null,
    val website: String? = null,
    val shortScreenshot: List<ScreenshotDomain>? = null,
    val publishers: List<PublisherDomain>? = null,
)

