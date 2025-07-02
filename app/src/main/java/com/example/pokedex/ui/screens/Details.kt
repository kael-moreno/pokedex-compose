package com.example.pokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.data.PokemonDetail
import com.example.pokedex.extensions.getImageLoader
import com.example.pokedex.ui.components.PokedexScaffold
import com.example.pokedex.ui.theme.Black
import com.example.pokedex.viewmodel.PokemonViewModel

@Composable
fun DetailsScreen(id: String?, navController: NavController? = null) {
    PokedexScaffold(
        title = "Pokemon Details",
        navController = navController,
        hasNavigationIcon = true,
        content = { innerPadding ->
            LoadPokemonDetails(id)
        }
    )
}

@Composable
fun LoadPokemonDetails(id: String?) {
    val viewModel = hiltViewModel<PokemonViewModel>()
    LaunchedEffect(id) {
        viewModel.getPokemonDetail(id)
    }
    val detailState = viewModel.pokemonDetail.collectAsState()
    val errorState = viewModel.error.collectAsState()

    val error = errorState.value
    val detail = detailState.value
    if (error != null) {
        Text(text = "Error: $error")
    } else if (detail != null) {
        PokemonDetails(detail)
    } else {
        CircularProgressIndicator()
        Text(text = "Loading details for Pokémon ID: $id")
    }
}

@Composable
fun PokemonDetails(detail: PokemonDetail) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = detail.sprites.other?.dreamWorld?.frontDefault,
                imageLoader = getImageLoader()
            ),
            contentDescription = detail.name,
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
        )
        Text(
            text = "#${detail.id} ${detail.name.replaceFirstChar { it.uppercase() }}",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 0.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Name: ${detail.name}",
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
        }
    }
}
