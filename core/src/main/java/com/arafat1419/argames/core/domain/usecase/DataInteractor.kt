package com.arafat1419.argames.core.domain.usecase

import androidx.paging.PagingData
import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.model.GenreDomain
import com.arafat1419.argames.core.domain.model.ListDomain
import com.arafat1419.argames.core.domain.model.ScreenshotDomain
import com.arafat1419.argames.core.domain.repository.IDataRepository
import com.arafat1419.argames.core.domain.repository.IPagingRepository
import com.arafat1419.argames.core.vo.Resource
import kotlinx.coroutines.flow.Flow

class DataInteractor(
    private val iDataRepository: IDataRepository,
    private val iPagingRepository: IPagingRepository
) : DataUseCase {
    override fun listGames(): Flow<Resource<ListDomain<GameDomain>>> =
        iDataRepository.listGames()

    override fun gamesGenres(): Flow<Resource<ListDomain<GenreDomain>>> =
        iDataRepository.gamesGenres()

    override fun gameDetail(gameId: Int): Flow<Resource<GameDomain>> =
        iDataRepository.gameDetail(gameId)

    override fun gameScreenshots(gameId: Int): Flow<Resource<ListDomain<ScreenshotDomain>>> =
        iDataRepository.gameScreenshots(gameId)

    override fun searchGames(
        search: String?,
        genreId: List<String>?
    ): Flow<PagingData<GameDomain>> =
        iPagingRepository.searchGames(search, genreId)

    override fun getFavoriteGames(): Flow<Resource<List<GameDomain>>> =
        iDataRepository.getFavoriteGames()

    override fun getFavoriteGamesId(): Flow<Resource<List<Int>>> =
        iDataRepository.getFavoriteGamesId()

    override fun isGameFavorite(gameId: Int): Flow<Resource<Boolean>> =
        iDataRepository.isGameFavorite(gameId)

    override suspend fun insertGame(game: GameDomain) =
        iDataRepository.insertGame(game)

    override suspend fun deleteGame(game: GameDomain) =
        iDataRepository.deleteGame(game)
}