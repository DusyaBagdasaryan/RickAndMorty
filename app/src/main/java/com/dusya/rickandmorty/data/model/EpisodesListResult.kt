package com.dusya.rickandmorty.data.model

import com.dusya.rickandmorty.data.model.episode.Episode

/**
 * Episodes list location request response result
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
data class EpisodesListResult(
    val info: Info,
    val results: List<Episode>
)