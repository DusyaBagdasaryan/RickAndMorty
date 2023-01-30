package com.dusya.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dusya.rickandmorty.api.Api
import com.dusya.rickandmorty.data.datasource.EpisodeDataSource
import com.dusya.rickandmorty.data.model.episode.Episode
import kotlinx.coroutines.flow.Flow

/**
 * Episode paging data repository
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class EpisodeRepository {
    fun fetchEpisodeList(): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(
                Api.pageSize,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                EpisodeDataSource()
            }, initialKey = 1
        ).flow
    }
}