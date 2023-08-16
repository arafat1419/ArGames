package com.arafat1419.argames.core.data.local

import com.arafat1419.argames.core.data.local.db.AppDao
import com.arafat1419.argames.core.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val appDao: AppDao) {
    fun getFavoriteGames(): Flow<List<GameEntity>> = appDao.getFavoriteGames()
    fun getFavoriteGamesId(): Flow<List<Int>> = appDao.getFavoriteGamesId()
    fun isGameFavorite(gameId: Int): Flow<Boolean> = appDao.isGamesFavorite(gameId)
    suspend fun insertGame(game: GameEntity) = appDao.insertGame(game)
    suspend fun deleteGame(game: GameEntity) = appDao.deleteGame(game)
}