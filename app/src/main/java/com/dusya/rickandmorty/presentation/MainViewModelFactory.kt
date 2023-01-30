package com.dusya.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dusya.rickandmorty.data.repository.RickAndMortyRepository

/**
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class MainViewModelFactory(private val repository: RickAndMortyRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewModelRickAndMorty::class.java)) {
            ViewModelRickAndMorty(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}