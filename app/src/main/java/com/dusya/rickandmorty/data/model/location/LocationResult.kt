package com.dusya.rickandmorty.data.model.location

import com.dusya.rickandmorty.data.model.Info

/**
 * Location list request response result
 */
data class LocationResult(
    val info: Info,
    val results: List<FullLocation>
)