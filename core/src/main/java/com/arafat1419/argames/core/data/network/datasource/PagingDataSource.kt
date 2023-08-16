package com.arafat1419.argames.core.data.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arafat1419.argames.core.data.network.api.ApiService
import com.arafat1419.argames.core.data.network.response.GameResponse
import retrofit2.HttpException
import java.io.IOException

class PagingDataSource(
    private val apiService: ApiService,
    private val search: String?,
    private val genresId: List<String>? = null
) :
    PagingSource<Int, GameResponse>() {
    override fun getRefreshKey(state: PagingState<Int, GameResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameResponse> {
        val pageIndex = params.key ?: 1
        val pageSize = 15
        return try {
            val response = apiService.listGames(search, pageIndex, pageSize, genresId?.toString())
            val games = response.results

            val nextKey = if (response.next == null) {
                null
            } else {
                if (
                    params.loadSize == 3 * pageSize
                ) {
                    pageIndex + 1
                } else {
                    pageIndex + (params.loadSize / pageSize)
                }
            }

            LoadResult.Page(
                data = games!!,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}