package com.arafat1419.argames.core.data.local

import com.arafat1419.argames.core.utils.FlowTestUtil.getOrAwaitValue
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.arafat1419.argames.core.data.local.db.AppDao
import com.arafat1419.argames.core.data.local.db.AppDatabase
import com.arafat1419.argames.core.utils.DataMapper
import com.arafat1419.argames.core.utils.DummyData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocalDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var appDao: AppDao
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        appDao = appDatabase.appDao()

        localDataSource = LocalDataSource(appDao)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun getFavoriteGames() = runBlocking {
        val dummyGame = DataMapper.games(DummyData.game!!)
        localDataSource.insertGame(dummyGame)

        val result = localDataSource.getFavoriteGames().getOrAwaitValue()

        assertEquals(listOf(dummyGame), result)
    }

    @Test
    fun getFavoriteGamesId() = runBlocking {
        val dummyGame = DataMapper.games(DummyData.game!!)
        val dummyGame2 = DataMapper.games(DummyData.listGames.results?.get(1)!!)

        localDataSource.insertGame(dummyGame)
        localDataSource.insertGame(dummyGame2)

        val result = localDataSource.getFavoriteGamesId().getOrAwaitValue()

        assertEquals(listOf(dummyGame.id, dummyGame2.id), result)
    }

    @Test
    fun isGameFavorite() = runBlocking {
        val dummyGame = DataMapper.games(DummyData.game!!)

        localDataSource.insertGame(dummyGame)

        val result = localDataSource.isGameFavorite(dummyGame.id!!).getOrAwaitValue()

        assertEquals(true, result)
    }

    @Test
    fun deleteGame() = runBlocking {
        val dummyGame = DataMapper.games(DummyData.game!!)
        val dummyGame2 = DataMapper.games(DummyData.listGames.results?.get(1)!!)

        localDataSource.insertGame(dummyGame)
        localDataSource.insertGame(dummyGame2)
        localDataSource.deleteGame(dummyGame)

        val result = localDataSource.getFavoriteGames().getOrAwaitValue()

        assertEquals(listOf(dummyGame2), result)
    }
}
