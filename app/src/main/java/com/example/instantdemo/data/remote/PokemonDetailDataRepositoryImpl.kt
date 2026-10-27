package com.example.instantdemo.data.remote

import com.example.instantdemo.data.network.PokemonDataSource
import com.example.instantdemo.models.PokemonDetailData
import jakarta.inject.Inject

class PokemonDetailDataRepositoryImpl @Inject constructor(
    private var pokemonDataSource: PokemonDataSource
) : PokemonDetailDataRepository{

    override suspend fun getPokemonDetailData(name: String): Result<PokemonDetailData> {
      return runCatching {
          pokemonDataSource.getPokemonDetailData(name)
      }
    }
}
