package com.dusya.rickandmorty.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dusya.rickandmorty.R
import com.dusya.rickandmorty.data.model.Person
import com.dusya.rickandmorty.databinding.PersonListItemBinding

/**
 * View Holder for person list item
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class PersonViewHolder(
    private val binding: PersonListItemBinding,
    private val onClickListener: OnClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    private var person: Person? = null

    init {
        binding.root.setOnClickListener {
            person?.let {
                onClickListener.onClick(it)
            }
        }
    }

    fun bindDataView(pers: Person?) {
        person = pers
        val person = pers ?: return
        Glide.with(binding.root)
            .load(person.image)
            .into(binding.avatar)
        binding.namePerson.text = person.name
        binding.indicator.text = "${person.status} - ${person.species}"
        binding.viewIndicator.setBackgroundResource(
            if (person.status == "Alive") {
                R.drawable.indicator_green
            } else {
                R.drawable.indicator_gray
            }
        )
        binding.firstSeenLocationValue.text = person.origin.name
        binding.lastSeenLocationValue.text = person.location.name
    }
}

interface OnClickListener {
    fun onClick(person: Person)
}