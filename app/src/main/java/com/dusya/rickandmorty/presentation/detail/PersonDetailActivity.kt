package com.dusya.rickandmorty.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dusya.rickandmorty.R
import com.dusya.rickandmorty.data.model.Person
import com.dusya.rickandmorty.data.model.episode.Episode
import com.dusya.rickandmorty.data.repository.EpisodeRepository

@OptIn(ExperimentalGlideComposeApi::class)
class PersonDetailActivity : ComponentActivity() {

    private lateinit var viewModel: EpisodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            EpisodeViewModelFactory(EpisodeRepository())
        )[EpisodeViewModel::class.java]
        val person = intent.extras?.getParcelable("person") as Person?
        setContent {
            person?.let {
                UserDetail(person = it)
            }
        }
    }

    @Composable
    fun UserDetail(person: Person) {
        Column(
            Modifier
                .background(color = Color.Gray)
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            GlideImage(
                model = person.image,
                contentDescription = "person_image",
                modifier = Modifier
                    .padding(all = 5.dp)
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.size(10.dp))
            Text(
                text = person.name,
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.size(10.dp))
            TextLightGray(text = "Live status")
            Row() {
                Icon(
                    painter = painterResource(if (person.status == "Alive") R.drawable.ic_alive else R.drawable.ic_not_alive),
                    contentDescription = null
                )
                Text(text = person.status, color = Color.White, fontSize = 20.sp)
            }

            Spacer(Modifier.size(10.dp))
            TextLightGray(text = "Species and Gender:")
            Text(
                text = "${person.species}(${person.gender})",
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(Modifier.size(10.dp))
            TextLightGray(text = "Last known location")
            Text(text = person.location.name, color = Color.White, fontSize = 20.sp)

            Spacer(Modifier.size(10.dp))
            TextLightGray(text = "First seen in")
            Text(text = person.origin.name, color = Color.White, fontSize = 20.sp)

            Spacer(Modifier.size(10.dp))
            Text(
                text = "Episode",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            EpisodesList(viewModel.fetchData().collectAsLazyPagingItems())
        }

    }

    @Composable
    fun EpisodesList(lazyPagingItems: LazyPagingItems<Episode>) {
        val showProgressBarState = remember { mutableStateOf(false) }
        if (showProgressBarState.value) {
            ShowProgressBar()
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
                    items(items = lazyPagingItems, key = { episode -> episode.id }) { episode ->
                        if (episode != null) {
                            EpisodeItem(episode = episode)
                        }
                    }
                }
            }
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
    fun ShowProgressBar() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun EpisodeItem(episode: Episode) {
        val roundedCornerShape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
        Column(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
                .background(Color.Gray)
                .background(Color.DarkGray, roundedCornerShape)
                .padding(20.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(3f),
                    text = episode.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = episode.episode,
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    textAlign = TextAlign.End
                )
            }
            Spacer(Modifier.size(5.dp))
            Text(text = episode.airDate, color = Color.White, fontSize = 15.sp)
        }
    }

    @Composable
    fun TextLightGray(text: String) {
        Text(text = text, color = Color.LightGray, fontSize = 15.sp)
    }
}