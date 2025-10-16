package com.example.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.PokemonListResult
import com.example.pokedex.data.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private val _pokemonList = MutableStateFlow<List<PokemonListResult>>(emptyList())
    val pokemonList: StateFlow<List<PokemonListResult>> = _pokemonList

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<Exception?>(null)
        private set

    private var offset = 0
    private val pageSize = 20
    private var endReached = false

    fun fetchPokemonList() {
        if (isLoading || endReached) return
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val newPokemons = repository.getPokemonList(pageSize, offset)
                if (newPokemons.isEmpty()) {
                    endReached = true
                } else {
                    _pokemonList.value = _pokemonList.value + newPokemons
                    offset += pageSize
                }
            } catch (e: Exception) {
                // Optionally handle error
                Log.e("PokemonViewModel", "Error fetching Pokémon list", e)
                error = e
                endReached = true
            } finally {
                isLoading = false
            }
        }
    }
}
