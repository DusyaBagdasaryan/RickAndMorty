package com.dusya.rickandmorty.presentation.fullLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dusya.rickandmorty.data.model.location.FullLocation
import com.dusya.rickandmorty.data.repository.LocationListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

class ViewModelLocationList(private val repository: LocationListRepository) : ViewModel() {


    fun fetchData(): Flow<PagingData<FullLocation>> {
        return repository.fetchLocationList()
            .cachedIn(viewModelScope)
            .filterNotNull()
    }
}