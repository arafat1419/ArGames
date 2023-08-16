package com.arafat1419.argames.core.data.network.repository

import com.arafat1419.argames.core.data.local.LocalDataSource
import com.arafat1419.argames.core.data.local.entities.GameEntity
import com.arafat1419.argames.core.data.network.NetworkBoundResource
import com.arafat1419.argames.core.data.network.api.ApiResponse
import com.arafat1419.argames.core.data.network.datasource.NetworkDataSource
import com.arafat1419.argames.core.data.network.response.GameResponse
import com.arafat1419.argames.core.data.network.response.GenreResponse
import com.arafat1419.argames.core.data.network.response.ListResponse
import com.arafat1419.argames.core.data.network.response.ScreenshotResponse
import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.model.GenreDomain
import com.arafat1419.argames.core.domain.model.ListDomain
import com.arafat1419.argames.core.domain.model.ScreenshotDomain
import com.arafat1419.argames.core.domain.repository.IDataRepository
import com.arafat1419.argames.core.utils.DataMapper
import com.arafat1419.argames.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class DataRepository(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : IDataRepository {

    override fun listGames(): Flow<Resource<ListDomain<GameDomain>>> =
        object : NetworkBoundResource<ListDomain<GameDomain>, ListResponse<GameResponse>>() {
            override fun shouldFetch(data: ListDomain<GameDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<ListResponse<GameResponse>>> =
                networkDataSource.listGames()

            override suspend fun load(data: ListResponse<GameResponse>): Flow<ListDomain<GameDomain>> =
                listOf(DataMapper.list(data, DataMapper::games)).asFlow()
        }.asFlow()

    override fun gamesGenres(): Flow<Resource<ListDomain<GenreDomain>>> =
        object : NetworkBoundResource<ListDomain<GenreDomain>, ListResponse<GenreResponse>>() {
            override fun shouldFetch(data: ListDomain<GenreDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<ListResponse<GenreResponse>>>? =
                networkDataSource.gamesGenres()

            override suspend fun load(data: ListResponse<GenreResponse>): Flow<ListDomain<GenreDomain>>? =
                listOf(DataMapper.list(data, DataMapper::genre)).asFlow()
        }.asFlow()

    override fun gameDetail(gameId: Int): Flow<Resource<GameDomain>> =
        object : NetworkBoundResource<GameDomain, GameResponse>() {
            override fun shouldFetch(data: GameDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<GameResponse>>? =
                networkDataSource.gameDetail(gameId)

            override suspend fun load(data: GameResponse): Flow<GameDomain>? =
                listOf(DataMapper.games(data)).asFlow()
        }.asFlow()

    override fun gameScreenshots(gameId: Int): Flow<Resource<ListDomain<ScreenshotDomain>>> =
        object :
            NetworkBoundResource<ListDomain<ScreenshotDomain>, ListResponse<ScreenshotResponse>>() {
            override fun shouldFetch(data: ListDomain<ScreenshotDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<ListResponse<ScreenshotResponse>>>? =
                networkDataSource.gameScreenshots(gameId)

            override suspend fun load(data: ListResponse<ScreenshotResponse>): Flow<ListDomain<ScreenshotDomain>>? =
                listOf(DataMapper.list(data, DataMapper::screenshot)).asFlow()
        }.asFlow()

    override fun getFavoriteGames(): Flow<Resource<List<GameDomain>>> =
        object : NetworkBoundResource<List<GameDomain>, List<GameEntity>>() {
            override fun shouldFetch(data: List<GameDomain>?): Boolean = false

            override fun loadFromDB(): Flow<List<GameDomain>>? =
                localDataSource.getFavoriteGames().map { listData ->
                    listData.map { data ->
                        DataMapper.games(data)
                    }
                }
        }.asFlow()

    override fun getFavoriteGamesId(): Flow<Resource<List<Int>>> =
        object : NetworkBoundResource<List<Int>, List<Int>>() {
            override fun shouldFetch(data: List<Int>?): Boolean = false

            override fun loadFromDB(): Flow<List<Int>>? =
                localDataSource.getFavoriteGamesId()
        }.asFlow()

    override fun isGameFavorite(gameId: Int): Flow<Resource<Boolean>> =
        object : NetworkBoundResource<Boolean, Boolean>() {
            override fun shouldFetch(data: Boolean?): Boolean = false

            override fun loadFromDB(): Flow<Boolean>? =
                localDataSource.isGameFavorite(gameId)
        }.asFlow()

    override suspend fun insertGame(game: GameDomain) = localDataSource.insertGame(
        DataMapper.games(game)
    )

    override suspend fun deleteGame(game: GameDomain) = localDataSource.deleteGame(
        DataMapper.games(game)
    )

}