package com.example.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.PokemonListResult
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.data.PokemonPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokedex.data.PokemonDetail
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private val pokemonDetailState = MutableStateFlow<PokemonDetail?>(null)
    val pokemonDetail = pokemonDetailState.asStateFlow()

    private val errorState = MutableStateFlow<String?>(null)
    val error = errorState.asStateFlow()

    val pagingPokemonList: Flow<PagingData<PokemonListResult>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { PokemonPagingSource(repository) }
    ).flow

    fun getPokemonDetail(id: String?) {
        if (id.isNullOrEmpty()) return

        viewModelScope.launch {
            try {
                val detail = repository.getPokemonDetail(id)
                pokemonDetailState.update {
                    detail
                }
                errorState.value = null // Clear error on success
            } catch (e: Exception) {
                pokemonDetailState.value = null
                errorState.value = e.localizedMessage ?: "Unknown error occurred"
            }
        }
    }
}
