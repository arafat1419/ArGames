package com.arafat1419.argames.core.data.network.datasource

import com.arafat1419.argames.core.data.network.api.ApiResponse
import com.arafat1419.argames.core.data.network.api.ApiService
import com.arafat1419.argames.core.data.network.response.GameResponse
import com.arafat1419.argames.core.data.network.response.GenreResponse
import com.arafat1419.argames.core.data.network.response.ListResponse
import com.arafat1419.argames.core.data.network.response.ScreenshotResponse
import com.arafat1419.argames.core.vo.ErrorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NetworkDataSource(private val apiService: ApiService) {

    suspend fun listGames(): Flow<ApiResponse<ListResponse<GameResponse>>> =
        networkHandler {
            apiService.listGames()
        }

    suspend fun gamesGenres(): Flow<ApiResponse<ListResponse<GenreResponse>>> =
        networkHandler {
            apiService.gamesGenres()
        }

    suspend fun gameDetail(gameId: Int): Flow<ApiResponse<GameResponse>> =
        networkHandler {
            apiService.gameDetail(gameId)
        }

    suspend fun gameScreenshots(gameId: Int): Flow<ApiResponse<ListResponse<ScreenshotResponse>>> =
        networkHandler {
            apiService.gameScreenshots(gameId)
        }

    private suspend fun <T> networkHandler(responseCall: suspend () -> T): Flow<ApiResponse<T>> =
        flow {
            try {
                val response = responseCall()

                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(ErrorData(null, e.message)))
            }
        }.flowOn(Dispatchers.IO)

}