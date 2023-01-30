package com.dusya.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dusya.rickandmorty.data.model.Person
import com.dusya.rickandmorty.data.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * ViewModel that handles data for persons list activity
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class ViewModelRickAndMorty(private val repository: RickAndMortyRepository) : ViewModel() {

    private val _dataFlow = MutableStateFlow<PagingData<Person>?>(null)
    val dataFlow: Flow<PagingData<Person>?> = _dataFlow

    fun fetchData() {
        repository.fetchPersonsList().onEach {
            _dataFlow.value = it
        }.launchIn(viewModelScope)
    }
}