package com.example.pokedex.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.pokedex.ui.components.PokedexScaffold

@Composable
fun DetailsScreen(id: String?, navController: NavController? = null) {
    PokedexScaffold(
        title = "Pokemon Details",
        navController = navController,
        hasNavigationIcon = true,
        content = { innerPadding ->
            Text(text = "Details for Pokémon ID: $id")
        }
    )
}

