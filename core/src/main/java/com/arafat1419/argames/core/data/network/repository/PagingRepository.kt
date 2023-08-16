package com.arafat1419.argames.core.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.arafat1419.argames.core.data.network.api.ApiService
import com.arafat1419.argames.core.data.network.datasource.PagingDataSource
import com.arafat1419.argames.core.domain.model.GameDomain
import com.arafat1419.argames.core.domain.repository.IPagingRepository
import com.arafat1419.argames.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PagingRepository(private val apiService: ApiService) : IPagingRepository {
    override fun searchGames(search: String?, genreId: List<String>?): Flow<PagingData<GameDomain>> =
        Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                PagingDataSource(apiService, search, genreId)
            }
        ).flow.map {
            it.map { game ->
                DataMapper.games(game)
            }
        }
}