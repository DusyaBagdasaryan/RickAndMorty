package com.dusya.rickandmorty.data.model.location

/**
 * Full information of location object
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
data class FullLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents : List<String>,
    val url: String,
    val created: String
)
