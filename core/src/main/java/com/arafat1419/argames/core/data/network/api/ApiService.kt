package com.arafat1419.argames.core.data.network.api

import com.arafat1419.argames.core.BuildConfig.API_KEY
import com.arafat1419.argames.core.data.network.response.GameResponse
import com.arafat1419.argames.core.data.network.response.GenreResponse
import com.arafat1419.argames.core.data.network.response.ListResponse
import com.arafat1419.argames.core.data.network.response.ScreenshotResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun listGames(
        @Query("search") search: String? = null,
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("genres") genres: String? = null,
        @Query("key") apiKey: String = API_KEY
    ): ListResponse<GameResponse>

    @GET("games")
    suspend fun gamesGenres(
        @Query("key") apiKey: String = API_KEY
    ): ListResponse<GenreResponse>

    @GET("games/{game_id}")
    suspend fun gameDetail(
        @Path("game_id") gameId: Int,
        @Query("key") apiKey: String = API_KEY
    ): GameResponse

    @GET("games/{game_id}/screenshots")
    suspend fun gameScreenshots(
        @Path("game_id") gameId: Int,
        @Query("key") apiKey: String = API_KEY
    ): ListResponse<ScreenshotResponse>
}