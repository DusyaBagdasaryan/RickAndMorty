package com.dusya.rickandmorty.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dusya.rickandmorty.api.Api
import com.dusya.rickandmorty.data.model.location.FullLocation

/**
 * Paging DataSource for loading locations list with pagination
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class FullLocationDataSource : PagingSource<Int, FullLocation>() {
    override fun getRefreshKey(state: PagingState<Int, FullLocation>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int): Int? {
        return if (key == 1) null else key - 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FullLocation> {
        val start = params.key ?: STARTING_KEY
        return try {
            LoadResult.Page(
                data = Api.retrofit.loadLocationList(start).results,
                prevKey = when (start) {
                    STARTING_KEY -> null
                    else -> ensureValidKey(start)
                },
                nextKey = start + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_KEY = 1
    }

}