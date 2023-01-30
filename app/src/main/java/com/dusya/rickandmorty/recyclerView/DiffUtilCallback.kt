package com.dusya.rickandmorty.recyclerView

import androidx.recyclerview.widget.DiffUtil
import com.dusya.rickandmorty.data.model.Person

/**
 * Diff util callback for Person's list adapter
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class DiffUtilCallback() : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

}