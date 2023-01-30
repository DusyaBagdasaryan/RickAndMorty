package com.dusya.rickandmorty.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.dusya.rickandmorty.data.model.Person
import com.dusya.rickandmorty.databinding.PersonListItemBinding

/**
 * Implementation of PagingDataAdapter for persons list
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class PersonsListAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<Person, PersonViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = PersonListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PersonViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bindDataView(getItem(position))
    }

}