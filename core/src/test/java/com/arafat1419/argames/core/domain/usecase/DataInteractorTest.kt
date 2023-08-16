package com.arafat1419.argames.core.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arafat1419.argames.core.data.network.repository.DataRepository
import com.arafat1419.argames.core.data.network.repository.PagingRepository
import com.arafat1419.argames.core.utils.DummyData
import com.arafat1419.argames.core.vo.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
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
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get list games failed`() = runBlocking {
        val dummyResource = Resource.Error(DummyData.errorCode, DummyData.listGames)
        `when`(dataRepository.listGames()).thenReturn(flowOf(dummyResource))

        dataInteractor.listGames().collect { result ->
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game detail success`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Success(DummyData.game)
        `when`(dataRepository.gameDetail(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameDetail(id).collect { result ->
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game detail failed`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Error(DummyData.errorCode, DummyData.game)
        `when`(dataRepository.gameDetail(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameDetail(id).collect { result ->
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game screenshots success`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Success(DummyData.screenshots)
        `when`(dataRepository.gameScreenshots(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameScreenshots(id).collect { result ->
            assertEquals(dummyResource, result)
        }
    }

    @Test
    fun `Get game screenshots failed`() = runBlocking {
        val id = DummyData.game?.id!!
        val dummyResource = Resource.Error(DummyData.errorCode, DummyData.screenshots)
        `when`(dataRepository.gameScreenshots(id)).thenReturn(flowOf(dummyResource))

        dataInteractor.gameScreenshots(id).collect { result ->
            assertEquals(dummyResource, result)
        }
    }
}