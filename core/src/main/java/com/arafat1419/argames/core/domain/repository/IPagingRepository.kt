package com.arafat1419.argames.core.domain.repository

import androidx.paging.PagingData
import com.arafat1419.argames.core.domain.model.GameDomain
import kotlinx.coroutines.flow.Flow

interface IPagingRepository {
    fun searchGames(search: String?, genreId: List<String>?): Flow<PagingData<GameDomain>>
}