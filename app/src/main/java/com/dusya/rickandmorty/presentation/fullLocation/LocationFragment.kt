package com.dusya.rickandmorty.presentation.fullLocation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.dusya.rickandmorty.data.model.location.FullLocation
import com.dusya.rickandmorty.data.repository.LocationListRepository
import com.dusya.rickandmorty.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private val viewModel: ViewModelLocationList by lazy {
        ViewModelProvider(
            this, LocationListViewModelFactory(LocationListRepository())
        )[ViewModelLocationList::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        if (view == null) {
            binding = FragmentLocationBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeViewBtn.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.Default)
            setContent {
                // In Compose world
                MaterialTheme {
                    LocationList(lazyPagingItems = viewModel.fetchData().collectAsLazyPagingItems())
                }
            }
        }
    }

    @Composable
    fun LocationList(lazyPagingItems: LazyPagingItems<FullLocation>) {
        val showProgressBarState = remember { mutableStateOf(false) }
        if (showProgressBarState.value) {
            ShowProgressBarState()
        }
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                showProgressBarState.value = true
            }
            is LoadState.Error -> {
                showProgressBarState.value = false
                ErrorRetry {
                    lazyPagingItems.retry()
                }
            }
            else -> {
                showProgressBarState.value = false
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(items = lazyPagingItems, key = { location -> location.id }) { location ->
                        if (location != null) {
                            LocationItem(location = location)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LocationItem(location: FullLocation) {
        val roundedCornerShape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
                .background(Color.Gray)
                .background(Color.DarkGray, roundedCornerShape)
                .padding(20.dp)
        ) {
            Text(
                text = location.name,
                color = Color.White,
                fontSize = 20.sp,
                maxLines = 2,
                textAlign = TextAlign.Start
            )
            Text(
                text = location.type,
                color = Color.LightGray,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = location.dimension,
                color = Color.LightGray,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = location.created,
                color = Color.LightGray,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.size(5.dp))
        }
    }

    @Composable
    private fun ErrorRetry(retryAction: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Something went wrong, try again",
                color = Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Button(onClick = retryAction) {
                Text(text = "Try again", color = Color.Green, fontSize = 15.sp)
            }
        }
    }

    @Composable
    fun ShowProgressBarState() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
    }

    companion object {
        const val TAG = "LocationFragment"
        fun newInstance(): LocationFragment {
            return LocationFragment()
        }
    }
}