package com.dusya.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dusya.rickandmorty.api.Api
import com.dusya.rickandmorty.data.datasource.PersonsListDataSource
import com.dusya.rickandmorty.data.model.Person
import kotlinx.coroutines.flow.Flow

/**
 * Person paging data repository
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class RickAndMortyRepository {

    /**
     * Fetches persons list with pagination
     */
    fun fetchPersonsList(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                Api.pageSize,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                PersonsListDataSource()
            }, initialKey = 1
        ).flow
    }
}