package com.example.pokedex.data

import javax.inject.Inject

class PokemonRepository
@Inject constructor(
    private val api: PokeApiService
) {
    suspend fun getPokemonList(limit: Int, offset: Int) = api.getPokemonList(limit, offset).results
}
