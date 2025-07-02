package com.example.pokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.data.PokemonListResult
import com.example.pokedex.extensions.getImageLoader
import com.example.pokedex.ui.theme.MdTeal500
import com.example.pokedex.viewmodel.PokemonViewModel
import com.example.pokedex.ui.components.PokedexScaffold
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.LazyPagingItems
import com.example.pokedex.ui.theme.MdGrey50
import com.example.pokedex.ui.theme.MdGrey600
import com.example.pokedex.ui.theme.MdLightBlue900
import com.example.pokedex.ui.theme.White
import androidx.compose.material3.Text as MaterialText

@Composable
fun Main(navController: NavController) {
    val pokemonViewModel: PokemonViewModel = hiltViewModel()
    val pagingPokemonList = pokemonViewModel.pagingPokemonList.collectAsLazyPagingItems()
    val listState = rememberLazyGridState()

    PokedexScaffold(
        title = "Pokedex",
        content = { innerPadding ->
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
    listState: LazyGridState,
    onItemClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items.itemCount) { index ->
            val item = items[index]
            if (item != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardColors(
                        containerColor = MdLightBlue900,
                        contentColor = MdLightBlue900,
                        disabledContainerColor = MdGrey600,
                        disabledContentColor = MdGrey600
                    )
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
                        Column {
                            MaterialText(
                                text = "#${item.id}",
                                color = White,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            MaterialText(
                                text = item.name.replaceFirstChar { it.uppercase() },
                                color = White,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
        item {
            when (items.loadState.append) {
                is LoadState.Loading -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                is LoadState.Error -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Snackbar(
                            modifier = Modifier.padding(16.dp),
                            action = {
                                TextButton(onClick = { items.retry() }) {
                                    Text("Retry")
                                }
                            }
                        ) {
                            Text("Failed to load more Pokémon.")
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
