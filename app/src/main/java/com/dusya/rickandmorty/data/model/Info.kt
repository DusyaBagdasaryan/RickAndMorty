package com.dusya.rickandmorty.data.model

/**
 * Response Info
 * [count] response elements count
 * [pages] count of pages that can be fetched
 * [next] url of next page or null(if the current one is tha last page)
 * [next] url of previous page or null(if the current one is the first page)
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)