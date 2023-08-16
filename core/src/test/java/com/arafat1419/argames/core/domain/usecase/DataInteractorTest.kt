package com.arafat1419.argames.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.arafat1419.argames.core.data.network.repository.DataRepository
import com.arafat1419.argames.core.data.network.repository.PagingRepository
import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.utils.DummyData
import com.arafat1419.argames.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class DataInteractorTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val dataRepository: DataRepository = mock()
    private val pagingRepository: PagingRepository = mock()

    private val dataInteractor: DataInteractor by lazy {
        DataInteractor(dataRepository, pagingRepository)
    }

    @Test
    fun `Get list games success`() = runBlocking {
        val dummyResource = Resource.Success(DummyData.listGames)
        `when`(dataRepository.listGames()).thenReturn(flowOf(dummyResource))

        dataInteractor.listGames().collect { result ->
            assert(result is Resource.Success)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get list games failed`() = runBlocking {
        val dummyResource = Resource.Error(DummyData.errorCode, DummyData.listGames)
        `when`(dataRepository.listGames()).thenReturn(flowOf(dummyResource))

        dataInteractor.listGames().collect { result ->
            assert(result is Resource.Error)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game detail success`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Success(DummyData.game!!)
        `when`(dataRepository.gameDetail(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameDetail(id).collect { result ->
            assert(result is Resource.Success)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game detail failed`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Error(DummyData.errorCode, DummyData.game)
        `when`(dataRepository.gameDetail(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameDetail(id).collect { result ->
            assert(result is Resource.Error)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game screenshots success`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Success(DummyData.screenshots)
        `when`(dataRepository.gameScreenshots(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameScreenshots(id).collect { result ->
            assert(result is Resource.Success)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game screenshots failed`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Error(DummyData.errorCode, DummyData.screenshots)
        `when`(dataRepository.gameScreenshots(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameScreenshots(id).collect { result ->
            assert(result is Resource.Error)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Test searchGames method`() = runBlocking {
        val keyword = "batman"
        val dummyPagingData: PagingData<GameDomain> = PagingData.from(listOf(DummyData.game!!))
        `when`(pagingRepository.searchGames(keyword, null)).thenReturn(flowOf(dummyPagingData))

        val result: Flow<PagingData<GameDomain>> = dataInteractor.searchGames(keyword, null)

        result.collect { pagingData ->
            assertEquals(dummyPagingData, pagingData)
        }
    }

    @Test
    fun `Get favorite games success`() = runBlocking {
        val dummyResource = Resource.Success(listOf<GameDomain>())
        `when`(dataRepository.getFavoriteGames()).thenReturn(flowOf(dummyResource))

        dataInteractor.getFavoriteGames().collect { result ->
            assert(result is Resource.Success)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get favorite games failed`() = runBlocking {
        val dummyResource = Resource.Error(DummyData.errorCode, listOf<GameDomain>())
        `when`(dataRepository.getFavoriteGames()).thenReturn(flowOf(dummyResource))

        dataInteractor.getFavoriteGames().collect { result ->
            assert(result is Resource.Error)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get favorite games id success`() = runBlocking {
        val dummyResource = Resource.Success(List(5) { (1..10).random() })
        `when`(dataRepository.getFavoriteGamesId()).thenReturn(flowOf(dummyResource))

        dataInteractor.getFavoriteGamesId().collect { result ->
            assert(result is Resource.Success)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get favorite games id failed`() = runBlocking {
        val dummyResource = Resource.Error(DummyData.errorCode, List(5) { (1..10).random() })
        `when`(dataRepository.getFavoriteGamesId()).thenReturn(flowOf(dummyResource))

        dataInteractor.getFavoriteGamesId().collect { result ->
            assert(result is Resource.Error)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get is game favorite success`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Success(true)
        `when`(dataRepository.isGameFavorite(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.isGameFavorite(id).collect { result ->
            assert(result is Resource.Success)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get is game favorite failed`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Error(DummyData.errorCode, false)
        `when`(dataRepository.isGameFavorite(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.isGameFavorite(id).collect { result ->
            assert(result is Resource.Error)
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Test insertGame method`() = runBlocking {
        val dummyGame = DummyData.game!!
        dataInteractor.insertGame(dummyGame)

        verify(dataRepository).insertGame(dummyGame)
    }

    @Test
    fun `Test deleteGame method`() = runBlocking {
        val dummyGame = DummyData.game!!
        dataInteractor.deleteGame(dummyGame)

        verify(dataRepository).deleteGame(dummyGame)
    }
}