package com.arafat1419.argames.core.domain.repository

import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.model.GenreDomain
import com.arafat1419.argames.core.domain.model.ListDomain
import com.arafat1419.argames.core.domain.model.ScreenshotDomain
import com.arafat1419.argames.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    fun listGames(): Flow<Resource<ListDomain<GameDomain>>>
    fun gamesGenres(): Flow<Resource<ListDomain<GenreDomain>>>
    fun gameDetail(gameId: Int): Flow<Resource<GameDomain>>
    fun gameScreenshots(gameId: Int): Flow<Resource<ListDomain<ScreenshotDomain>>>

    fun getFavoriteGames(): Flow<Resource<List<GameDomain>>>
    fun getFavoriteGamesId(): Flow<Resource<List<Int>>>
    fun isGameFavorite(gameId: Int): Flow<Resource<Boolean>>
    suspend fun insertGame(game: GameDomain)
    suspend fun deleteGame(game: GameDomain)
}