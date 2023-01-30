package com.dusya.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dusya.rickandmorty.api.Api
import com.dusya.rickandmorty.data.datasource.FullLocationDataSource
import com.dusya.rickandmorty.data.model.location.FullLocation
import kotlinx.coroutines.flow.Flow

/**
 * Location paging data repository
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class LocationListRepository {
    fun fetchLocationList(): Flow<PagingData<FullLocation>> {
        return Pager(
            config = PagingConfig(
                Api.pageSize,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                FullLocationDataSource()
            }, initialKey = 1
        ).flow
    }
}