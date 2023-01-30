package com.dusya.rickandmorty.data.model

/**
 * Person list request response result
 * [info] response information
 * [results] list of persons
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
data class PersonsListResult(
    val info: Info,
    val results: List<Person>
)