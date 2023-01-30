package com.dusya.rickandmorty.data.model.episode

import com.google.gson.annotations.SerializedName

/**
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class Episode(
    val id: Int,
    val name: String,

    @SerializedName("air_date")
    val airDate: String,

    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)