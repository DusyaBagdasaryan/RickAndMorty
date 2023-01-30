package com.dusya.rickandmorty.presentation.fullLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dusya.rickandmorty.data.repository.LocationListRepository

class LocationListViewModelFactory(private val repository: LocationListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewModelLocationList::class.java)) {
            ViewModelLocationList(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}