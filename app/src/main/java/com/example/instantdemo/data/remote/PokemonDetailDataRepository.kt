package com.example.instantdemo.data.remote

import com.example.instantdemo.models.Pokemon
import com.example.instantdemo.models.PokemonDetailData

interface PokemonDetailDataRepository {

    suspend fun getPokemonDetailData(name: String) : Result<PokemonDetailData>

}