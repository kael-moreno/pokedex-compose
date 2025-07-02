package com.example.pokedex.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.data.PokemonListResult
import com.example.pokedex.extensions.getImageLoader
import com.example.pokedex.ui.theme.MdTeal500
import com.example.pokedex.viewmodel.PokemonViewModel
import com.example.pokedex.ui.components.PokedexScaffold
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.LazyPagingItems
import kotlin.getValue
import androidx.compose.material3.Text as MaterialText

@Composable
fun Main(navController: NavController) {
    val pokemonViewModel: PokemonViewModel = hiltViewModel()
    val pagingPokemonList = pokemonViewModel.pagingPokemonList.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    PokedexScaffold(
        title = "Pokedex",
        content = { innerPadding ->
            MaterialText(
                text = "Welcome to Pokedex!",
                modifier = Modifier.fillMaxWidth()
            )
            SimplePagingList(
                items = pagingPokemonList,
                listState = listState,
                onItemClick = { id -> navController.navigate("details/$id") }
            )
        }
    )
}

@Composable
fun SimplePagingList(
    items: LazyPagingItems<PokemonListResult>,
    listState: LazyListState,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(state = listState) {
        items(items.itemCount) { index ->
            val item = items[index]
            if (item != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 0.dp)
                        .clickable { onItemClick(item.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = item.imageUrl,
                                imageLoader = getImageLoader()
                            ),
                            contentDescription = item.name,
                            modifier = Modifier.size(48.dp)
                        )
                        MaterialText(
                            text = item.name.replaceFirstChar { it.uppercase() },
                            color = MdTeal500,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
        item {
            when (items.loadState.append) {
                is androidx.paging.LoadState.Loading -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                is androidx.paging.LoadState.Error -> {
                    // Optionally show error UI
                }
                else -> {}
            }
        }
    }
}
