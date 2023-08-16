package com.arafat1419.argames.core.utils

import com.arafat1419.argames.core.data.local.entities.GameEntity
import com.arafat1419.argames.core.data.network.response.GameResponse
import com.arafat1419.argames.core.data.network.response.GenreResponse
import com.arafat1419.argames.core.data.network.response.ListResponse
import com.arafat1419.argames.core.data.network.response.PublisherResponse
import com.arafat1419.argames.core.data.network.response.ScreenshotResponse
import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.model.GenreDomain
import com.arafat1419.argames.core.domain.model.ListDomain
import com.arafat1419.argames.core.domain.model.PublisherDomain
import com.arafat1419.argames.core.domain.model.ScreenshotDomain

object DataMapper {
    fun <T, R>list(input: ListResponse<T>, converter: (T) -> R): ListDomain<R> =
        ListDomain(
            input.count,
            input.next,
            input.previous,
            input.results?.map {
                converter(it)
            }
        )

    fun <T, R>list(input: ListDomain<T>, converter: (T) -> R): ListResponse<R> =
        ListResponse(
            input.count,
            input.next,
            input.previous,
            input.results?.map {
                converter(it)
            }
        )

    fun games(input: GameResponse): GameDomain =
        GameDomain(
            input.id,
            input.name,
            input.backgroundImage,
            input.released,
            input.description,
            input.rating,
            input.playtime,
            input.website,
            input.shortScreenshots?.map {
                screenshot(it)
            },
            input.publishers?.map {
                publisher(it)
            }
        )

    fun gamesResponse(input: GameDomain): GameResponse =
        GameResponse(
            input.id,
            input.name,
            input.backgroundImage,
            input.released,
            input.description,
            input.rating,
            input.playtime,
            input.website,
            input.shortScreenshots?.map {
                screenshot(it)
            },
            input.publishers?.map {
                publisher(it)
            }
        )

    fun games(input: GameEntity): GameDomain =
        GameDomain(
            id = input.id,
            name = input.name,
            backgroundImage = input.backgroundImage,
            released = input.released,
            rating = input.rating,
        )

    fun games(input: GameDomain): GameEntity =
        GameEntity(
            id = input.id,
            name = input.name,
            backgroundImage = input.backgroundImage,
            released = input.released,
            rating = input.rating,
        )

    fun genre(input: GenreResponse): GenreDomain =
        GenreDomain(
            input.id,
            input.name
        )

    fun screenshot(input: ScreenshotResponse): ScreenshotDomain =
        ScreenshotDomain(
            input.id,
            input.image
        )

    fun screenshot(input: ScreenshotDomain): ScreenshotResponse =
        ScreenshotResponse(
            input.id,
            input.image
        )

    fun publisher(input: PublisherResponse): PublisherDomain =
        PublisherDomain(
            input.id,
            input.name
        )

    fun publisher(input: PublisherDomain): PublisherResponse =
        PublisherResponse(
            input.id,
            input.name
        )
}