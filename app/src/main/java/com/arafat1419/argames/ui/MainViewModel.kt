package com.arafat1419.argames.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.model.GenreDomain
import com.arafat1419.argames.core.domain.model.ListDomain
import com.arafat1419.argames.core.domain.model.ScreenshotDomain
import com.arafat1419.argames.core.domain.usecase.DataUseCase
import com.arafat1419.argames.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val dataUseCase: DataUseCase) : ViewModel() {
    fun listGames(): LiveData<Resource<ListDomain<GameDomain>>> =
        dataUseCase.listGames().asLiveData()

    fun gamesGenres(): LiveData<Resource<ListDomain<GenreDomain>>> =
        dataUseCase.gamesGenres().asLiveData()

    fun gameDetail(gameId: Int): LiveData<Resource<GameDomain>> =
        dataUseCase.gameDetail(gameId).asLiveData()

    fun gameScreenshots(gameId: Int): LiveData<Resource<ListDomain<ScreenshotDomain>>> =
        dataUseCase.gameScreenshots(gameId).asLiveData()

    fun searchGames(
        search: String? = null,
        genreId: List<String>? = null
    ): Flow<PagingData<GameDomain>> =
        dataUseCase.searchGames(search, genreId).cachedIn(viewModelScope)

    fun getFavoriteGames(): LiveData<Resource<List<GameDomain>>> =
        dataUseCase.getFavoriteGames().asLiveData()

    fun getFavoriteGamesId(): LiveData<Resource<List<Int>>> =
        dataUseCase.getFavoriteGamesId().asLiveData()

    fun isGamesFavorite(gameId: Int) =
        dataUseCase.isGameFavorite(gameId).asLiveData()

    fun insertGame(game: GameDomain) =
        viewModelScope.launch {
            dataUseCase.insertGame(game)
        }

    fun deleteGame(game: GameDomain) =
        viewModelScope.launch {
            dataUseCase.deleteGame(game)
        }
}