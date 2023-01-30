package com.dusya.rickandmorty.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dusya.rickandmorty.data.repository.EpisodeRepository

class EpisodeViewModelFactory(private val repository: EpisodeRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EpisodeViewModel::class.java)) {
            EpisodeViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}