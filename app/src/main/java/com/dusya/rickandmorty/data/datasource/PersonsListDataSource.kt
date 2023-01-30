package com.dusya.rickandmorty.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dusya.rickandmorty.api.Api
import com.dusya.rickandmorty.data.model.Person

/**
 * Paging DataSource for loading persons list with pagination
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class PersonsListDataSource : PagingSource<Int, Person>() {
    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val start = params.key ?: STARTING_KEY
        return LoadResult.Page(
            data = Api.retrofit.loadList(start).results,
            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(start)
            },
            nextKey = start + 1
        )
    }

    private fun ensureValidKey(key: Int): Int? {
        return if (key == 1) null else key - 1
    }

    companion object {
        private const val STARTING_KEY = 1
    }

}