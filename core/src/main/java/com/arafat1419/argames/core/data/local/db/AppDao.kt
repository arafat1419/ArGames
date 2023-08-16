package com.arafat1419.argames.core.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arafat1419.argames.core.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM games_entities")
    fun getFavoriteGames(): Flow<List<GameEntity>>

    @Query("SELECT id FROM games_entities")
    fun getFavoriteGamesId(): Flow<List<Int>>

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM games_entities WHERE id = :gameId) THEN 1 ELSE 0 END")
    fun isGamesFavorite(gameId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = GameEntity::class)
    suspend fun insertGame(game: GameEntity)

    @Delete(entity = GameEntity::class)
    suspend fun deleteGame(game: GameEntity)
}