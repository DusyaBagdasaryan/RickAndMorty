package com.dusya.rickandmorty.api

import com.dusya.rickandmorty.data.model.EpisodesListResult
import com.dusya.rickandmorty.data.model.PersonsListResult
import com.dusya.rickandmorty.data.model.location.LocationResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api client interface for requests to remote server
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
interface Api {
    @GET("/api/character")
    suspend fun loadList(@Query("page") page: Int): PersonsListResult

    @GET("/api/episode")
    suspend fun loadEpisodeList(@Query("page") page: Int): EpisodesListResult

    @GET("/api/location")
    suspend fun loadLocationList(@Query("page") page: Int): LocationResult

    companion object {
        const val pageSize = 20

        val retrofit by lazy {
            Retrofit
                .Builder()
                .baseUrl("https://rickandmortyapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create<Api>()
        }
    }
}
