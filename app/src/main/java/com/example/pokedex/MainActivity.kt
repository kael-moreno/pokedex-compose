package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.Text as MaterialText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedex.ui.theme.MdRed800
import com.example.pokedex.ui.theme.MdTeal500
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.ui.theme.White
import com.example.pokedex.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    val pokemonViewModel: PokemonViewModel = hiltViewModel()
    val pokemonList = pokemonViewModel.pokemonList.collectAsState().value
    val isLoading by pokemonViewModel::isLoading
    val listState = rememberLazyListState()

    pokemonViewModel.fetchPokemonList()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = MdRed800,
                    titleContentColor = White,
                    scrolledContainerColor = White,
                    navigationIconContentColor = White,
                    actionIconContentColor = White
                ),
                title = { MaterialText("Pokedex") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding() + 16.dp,
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            MaterialText(
                text = "Welcome to Pokedex!",
                modifier = Modifier.fillMaxWidth()
            )
            if (isLoading && pokemonList.isEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(androidx.compose.ui.Alignment.CenterHorizontally)
                    )
                }
            } else {
                SimpleList(
                    items = pokemonList.map { it.name },
                    onLoadMore = { pokemonViewModel.fetchPokemonList() },
                    listState = listState,
                    isLoading = isLoading && pokemonList.isNotEmpty()
                )
                if (isLoading && pokemonList.isNotEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(androidx.compose.ui.Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleList(items: List<String>, onLoadMore: () -> Unit, listState: LazyListState, isLoading: Boolean = false) {
    LazyColumn(state = listState) {
        itemsIndexed(items) { index, item ->
            MaterialText(
                text = item,
                color = MdTeal500,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            if (index == items.lastIndex) {
                onLoadMore()
            }
        }
        if (isLoading) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(androidx.compose.ui.Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        // Main()
    }
}

