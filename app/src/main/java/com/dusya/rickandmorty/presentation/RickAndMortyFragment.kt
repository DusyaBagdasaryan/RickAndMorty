package com.dusya.rickandmorty.presentation

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.dusya.rickandmorty.data.model.Person
import com.dusya.rickandmorty.data.repository.RickAndMortyRepository
import com.dusya.rickandmorty.databinding.FragmentRickAndMortyBinding
import com.dusya.rickandmorty.presentation.detail.PersonDetailActivity
import com.dusya.rickandmorty.recyclerView.OnClickListener
import com.dusya.rickandmorty.recyclerView.PersonsListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @author Dusya Bagdasaryan on 2023.01.23
 */
class RickAndMortyFragment : Fragment() {
    private lateinit var binding: FragmentRickAndMortyBinding

    private val viewModel: ViewModelRickAndMorty by lazy {
        ViewModelProvider(
            this@RickAndMortyFragment,
            MainViewModelFactory(RickAndMortyRepository())
        )[ViewModelRickAndMorty::class.java]
    }

    private val onItemClickListener = object : OnClickListener {
        override fun onClick(person: Person) {
            val intent = Intent(requireContext(), PersonDetailActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }
    }
    private var personsListAdapter = PersonsListAdapter(onItemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRickAndMortyBinding.inflate(inflater, container, false)
        initRecyclerView()
        initReload()
        viewModel.fetchData()
        viewModel.dataFlow.onEach { pagingData ->
            pagingData?.let {
                personsListAdapter = PersonsListAdapter(onItemClickListener)
                personsListAdapter.submitData(lifecycle, it)
                binding.personsList.adapter = personsListAdapter
                initReload()
            }
        }.launchIn(lifecycleScope)
        return binding.root
    }

    private fun initReload() {
        personsListAdapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.progress.isVisible = true
            else {
                binding.progress.isVisible = false
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                binding.reloadButton.isVisible = errorState != null
            }
        }
    }


    private fun initRecyclerView() {
        with(binding.personsList) {
            adapter = personsListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.set(10, 10, 10, 10)
                }
            })
        }
    }

    companion object {
        const val TAG = "RickAndMortyFragment"
        fun newInstance(): RickAndMortyFragment {
            return RickAndMortyFragment()
        }
    }

}