package com.example.pokedex.data

import com.google.gson.annotations.SerializedName

// Data class for a single Pokemon entry in the list
 data class PokemonListResult(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

// Data class for the API response
 data class PokemonListResponse(
    @SerializedName("results") val results: List<PokemonListResult>
)
