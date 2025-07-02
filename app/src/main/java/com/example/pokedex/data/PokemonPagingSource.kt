package com.example.pokedex.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PokemonPagingSource(
    private val repository: PokemonRepository
) : PagingSource<Int, PokemonListResult>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListResult> {
        val offset = params.key ?: 0
        val pageSize = params.loadSize
        return try {
            val pokemons = repository.getPokemonList(pageSize, offset)
            val nextKey = if (pokemons.isEmpty()) null else offset + pageSize
            LoadResult.Page(
                data = pokemons,
                prevKey = if (offset == 0) null else offset - pageSize,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonListResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}

