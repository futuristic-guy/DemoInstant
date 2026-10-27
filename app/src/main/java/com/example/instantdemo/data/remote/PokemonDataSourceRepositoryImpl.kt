package com.example.instantdemo.data.remote

import com.example.instantdemo.data.network.PokemonDataSource
import com.example.instantdemo.models.Pokemon
import javax.inject.Inject

class PokemonDataSourceRepositoryImpl  @Inject constructor(
    private val pokemonDataSource: PokemonDataSource
) : PokemonDataRepository {

    override suspend fun getPokemonData(
        limit: Int,
        offset: Int
    ): Result<Pokemon> {
        return  runCatching {
            pokemonDataSource.getPokemonData(limit, offset)
        }
    }
}