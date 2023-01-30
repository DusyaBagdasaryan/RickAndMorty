package com.dusya.rickandmorty.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dusya.rickandmorty.data.model.episode.Episode
import com.dusya.rickandmorty.data.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class EpisodeViewModel(private val repository: EpisodeRepository) : ViewModel() {

    fun fetchData(): Flow<PagingData<Episode>> {
        return repository.fetchEpisodeList()
            .cachedIn(viewModelScope)
            .filterNotNull()
    }
}